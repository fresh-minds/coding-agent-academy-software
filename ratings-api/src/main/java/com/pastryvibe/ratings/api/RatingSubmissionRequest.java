package com.pastryvibe.ratings.api;

import com.pastryvibe.ratings.domain.FlavorTag;
import com.pastryvibe.ratings.domain.PastryId;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Validated request payload for submitting a pastry rating.
 *
 * The request is intentionally constrained so the services only accept known pastries,
 * a bounded number of flavors, and a short free-text experience field.
 */
public record RatingSubmissionRequest(
        @NotNull PastryId pastryId,
        @NotBlank String userId,
        @NotNull @Min(1) @Max(5) Integer score,
        @NotEmpty @Size(max = 3) List<@NotNull FlavorTag> flavors,
        @NotBlank @Size(max = 2000) String experience
) {
}
