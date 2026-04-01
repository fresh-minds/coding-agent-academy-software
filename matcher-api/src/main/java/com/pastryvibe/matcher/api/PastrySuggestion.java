package com.pastryvibe.matcher.api;

import com.pastryvibe.matcher.domain.FlavorTag;
import com.pastryvibe.matcher.domain.PastryId;

import java.util.List;

public record PastrySuggestion(
        PastryId pastryId,
        String pastryName,
        double averageRating,
        int ratingCount,
        List<FlavorTag> matchedFlavors,
        String lastReviewDescription
) {
}
