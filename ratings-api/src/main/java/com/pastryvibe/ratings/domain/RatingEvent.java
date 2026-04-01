package com.pastryvibe.ratings.domain;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
