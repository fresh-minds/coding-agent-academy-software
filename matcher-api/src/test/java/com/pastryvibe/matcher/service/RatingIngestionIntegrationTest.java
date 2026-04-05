package com.pastryvibe.matcher.service;

import com.pastryvibe.matcher.MatcherApiApplication;
import com.pastryvibe.matcher.domain.FlavorTag;
import com.pastryvibe.matcher.domain.PastryId;
import com.pastryvibe.matcher.domain.RatingEvent;
import com.pastryvibe.matcher.persistence.PastryRatingRepository;
import com.pastryvibe.matcher.persistence.PastryStatsEntity;
import com.pastryvibe.matcher.persistence.PastryStatsRepository;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@Testcontainers
@SpringBootTest(classes = MatcherApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RatingIngestionIntegrationTest {

    private static final String RATINGS_TOPIC = "pastry.ratings.v1";

    @Container
    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>(DockerImageName.parse("mysql:8.4.0"))
            .withDatabaseName("pastry_matcher")
            .withUsername("root")
            .withPassword("root");

    @Container
    private static final KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.1"));

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
    }

    @Autowired
    private PastryRatingRepository pastryRatingRepository;

    @Autowired
    private PastryStatsRepository pastryStatsRepository;

    @Test
    void consumesPersistsAndDeduplicatesRatingEvents() throws Exception {
        DefaultKafkaProducerFactory<String, RatingEvent> producerFactory = producerFactory();
        KafkaTemplate<String, RatingEvent> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        try {
            String pastryId = PastryId.STROOPWAFEL.name();
            PastryStatsEntity before = pastryStatsRepository.findById(pastryId).orElseThrow();
            int beforeCount = before.getRatingCount();
            double beforeAverage = before.getAverageScore();

            UUID eventId = UUID.randomUUID();
            Instant submittedAt = Instant.parse("2024-03-15T10:15:30Z");
            RatingEvent event = new RatingEvent(
                    eventId,
                    PastryId.STROOPWAFEL,
                    "user-42",
                    5,
                    List.of(FlavorTag.SWEET, FlavorTag.BUTTERY),
                    "Crisp outside and caramel inside.",
                    submittedAt
            );

            kafkaTemplate.send(RATINGS_TOPIC, event.eventId().toString(), event).get(10, TimeUnit.SECONDS);

            waitUntil(() -> pastryRatingRepository.existsByEventId(eventId.toString()), Duration.ofSeconds(20), "event to be persisted");
            waitUntil(() -> pastryRatingRepository.countByPastryId(pastryId) == beforeCount + 1, Duration.ofSeconds(20), "rating count to increment");

            PastryStatsEntity after = pastryStatsRepository.findById(pastryId).orElseThrow();
            double expectedAverage = ((beforeAverage * beforeCount) + event.score()) / (beforeCount + 1);

            assertThat(after.getRatingCount()).isEqualTo(beforeCount + 1);
            assertThat(after.getAverageScore()).isCloseTo(expectedAverage, within(0.0001));
            assertThat(after.getLastReviewSubmittedAt()).isEqualTo(submittedAt);
            assertThat(after.getLastReviewUserId()).isEqualTo("user-42");
            assertThat(after.getLastReviewScore()).isEqualTo(5);
            assertThat(after.getLastReviewExperience()).isEqualTo("Crisp outside and caramel inside.");
            assertThat(after.getLastReviewFlavors()).isEqualTo("SWEET,BUTTERY");

            kafkaTemplate.send(RATINGS_TOPIC, event.eventId().toString(), event).get(10, TimeUnit.SECONDS);

            assertStable(() -> pastryRatingRepository.countByPastryId(pastryId) == beforeCount + 1, Duration.ofSeconds(10), "duplicate event to be ignored");
            PastryStatsEntity afterDuplicate = pastryStatsRepository.findById(pastryId).orElseThrow();
            assertThat(afterDuplicate.getRatingCount()).isEqualTo(beforeCount + 1);
            assertThat(afterDuplicate.getAverageScore()).isCloseTo(expectedAverage, within(0.0001));
        } finally {
            producerFactory.destroy();
        }
    }

    private static DefaultKafkaProducerFactory<String, RatingEvent> producerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA.getBootstrapServers());
        JsonSerializer<RatingEvent> valueSerializer = new JsonSerializer<>();
        valueSerializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory<>(properties, new StringSerializer(), valueSerializer);
    }

    private static void waitUntil(BooleanSupplier condition, Duration timeout, String description) throws InterruptedException {
        long deadline = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < deadline) {
            if (condition.getAsBoolean()) {
                return;
            }
            Thread.sleep(200);
        }
        fail("Timed out waiting for " + description);
    }

    private static void assertStable(BooleanSupplier condition, Duration duration, String description) throws InterruptedException {
        long deadline = System.nanoTime() + duration.toNanos();
        while (System.nanoTime() < deadline) {
            if (!condition.getAsBoolean()) {
                fail("Condition stopped being true while waiting for " + description);
            }
            Thread.sleep(200);
        }
    }

    private static org.assertj.core.data.Offset<Double> within(double tolerance) {
        return org.assertj.core.data.Offset.offset(tolerance);
    }
}
