package com.pastryvibe.ratings.service;

import com.pastryvibe.ratings.api.RatingSubmissionRequest;
import com.pastryvibe.ratings.domain.RatingEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RatingPublisherService {

    private final KafkaTemplate<String, RatingEvent> kafkaTemplate;
    private final String ratingsTopic;

    public RatingPublisherService(
            KafkaTemplate<String, RatingEvent> kafkaTemplate,
            @Value("${app.kafka.topics.ratings}") String ratingsTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.ratingsTopic = ratingsTopic;
    }

    public UUID publish(RatingSubmissionRequest request) {
        RatingEvent event = new RatingEvent(
                UUID.randomUUID(),
                request.pastryId(),
                request.userId(),
                request.score(),
                request.flavors(),
                request.experience(),
                Instant.now()
        );

        kafkaTemplate.send(ratingsTopic, request.pastryId().name(), event);
        return event.eventId();
    }
}
