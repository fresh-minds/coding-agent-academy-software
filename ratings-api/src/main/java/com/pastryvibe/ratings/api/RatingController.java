package com.pastryvibe.ratings.api;

import com.pastryvibe.ratings.service.RatingPublisherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final RatingPublisherService ratingPublisherService;

    public RatingController(RatingPublisherService ratingPublisherService) {
        this.ratingPublisherService = ratingPublisherService;
    }

    @PostMapping
    public ResponseEntity<RatingAcceptedResponse> submitRating(@Valid @RequestBody RatingSubmissionRequest request) {
        UUID eventId = ratingPublisherService.publish(request);
        RatingAcceptedResponse response = new RatingAcceptedResponse(
                eventId,
                Instant.now(),
                "Rating accepted and queued for matching."
        );
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
