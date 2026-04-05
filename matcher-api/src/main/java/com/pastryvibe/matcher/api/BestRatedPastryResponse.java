package com.pastryvibe.matcher.api;

import com.pastryvibe.matcher.domain.PastryId;

public record BestRatedPastryResponse(
        PastryId pastryId,
        String pastryName,
        double averageRating,
        int ratingCount,
        String lastReviewDescription
) {
}
