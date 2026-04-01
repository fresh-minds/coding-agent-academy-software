package com.pastryvibe.ratings.api;

import java.time.Instant;
import java.util.UUID;

public record RatingAcceptedResponse(
        UUID eventId,
        Instant acceptedAt,
        String message
) {
}
