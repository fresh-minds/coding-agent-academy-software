package com.pastryvibe.matcher.domain;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Kafka event consumed by the matcher service to persist ratings and statistics.
 */
public record RatingEvent(
        UUID eventId,
        PastryId pastryId,
        String userId,
        int score,
        List<FlavorTag> flavors,
        String experience,
        Instant submittedAt
) {
}
