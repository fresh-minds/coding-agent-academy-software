package com.pastryvibe.ratings.api;

import java.time.Instant;
import java.util.UUID;

/**
 * Response returned when a rating has been accepted for asynchronous processing.
 */
public record RatingAcceptedResponse(
        UUID eventId,
        Instant acceptedAt,
        String message
) {
}
