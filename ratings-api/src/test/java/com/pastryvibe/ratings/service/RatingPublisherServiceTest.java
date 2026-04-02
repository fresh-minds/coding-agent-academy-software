package com.pastryvibe.ratings.service;

import com.pastryvibe.ratings.api.RatingSubmissionRequest;
import com.pastryvibe.ratings.domain.FlavorTag;
import com.pastryvibe.ratings.domain.PastryId;
import com.pastryvibe.ratings.domain.RatingEvent;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

class RatingPublisherServiceTest {

    @Test
    void publishUsesPastryIdAsKafkaMessageKey() {
        RecordingKafkaTemplate kafkaTemplate = new RecordingKafkaTemplate();
        RatingPublisherService service = new RatingPublisherService(kafkaTemplate, "ratings-topic");
        RatingSubmissionRequest request = new RatingSubmissionRequest(
                PastryId.STROOPWAFEL,
                "user-42",
                5,
                List.of(FlavorTag.CARAMEL, FlavorTag.SPICED),
                "Excellent"
        );

        UUID actualEventId = service.publish(request);

        assertThat(kafkaTemplate.topic).isEqualTo("ratings-topic");
        assertThat(kafkaTemplate.key).isEqualTo(PastryId.STROOPWAFEL.name());
        assertThat(actualEventId).isEqualTo(kafkaTemplate.event.eventId());
        assertThat(kafkaTemplate.event.pastryId()).isEqualTo(PastryId.STROOPWAFEL);
        assertThat(kafkaTemplate.event.userId()).isEqualTo("user-42");
        assertThat(kafkaTemplate.event.score()).isEqualTo(5);
        assertThat(kafkaTemplate.event.flavors()).containsExactly(FlavorTag.CARAMEL, FlavorTag.SPICED);
        assertThat(kafkaTemplate.event.experience()).isEqualTo("Excellent");
    }

    private static final class RecordingKafkaTemplate extends KafkaTemplate<String, RatingEvent> {

        private String topic;
        private String key;
        private RatingEvent event;

        private RecordingKafkaTemplate() {
            super(new DefaultKafkaProducerFactory<>(Map.of()));
        }

        @Override
        public CompletableFuture<SendResult<String, RatingEvent>> send(String topic, String key, RatingEvent data) {
            this.topic = topic;
            this.key = key;
            this.event = data;
            return CompletableFuture.completedFuture(null);
        }
    }
}
