package com.pastryvibe.matcher.service;

import com.pastryvibe.matcher.api.PastrySuggestion;
import com.pastryvibe.matcher.api.SuggestionResponse;
import com.pastryvibe.matcher.domain.FlavorTag;
import com.pastryvibe.matcher.domain.PastryId;
import com.pastryvibe.matcher.persistence.PastryEntity;
import com.pastryvibe.matcher.persistence.PastryRatingEntity;
import com.pastryvibe.matcher.persistence.PastryRatingRepository;
import com.pastryvibe.matcher.persistence.PastryRepository;
import com.pastryvibe.matcher.persistence.PastryStatsEntity;
import com.pastryvibe.matcher.persistence.PastryStatsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuggestionServiceTest {

    @Mock
    private PastryRepository pastryRepository;

    @Mock
    private PastryRatingRepository pastryRatingRepository;

    @Mock
    private PastryStatsRepository pastryStatsRepository;

    @InjectMocks
    private SuggestionService suggestionService;

    @Test
    void rejectsEmptyFlavorRequests() {
        assertThatThrownBy(() -> suggestionService.suggest(List.of()))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("At least one flavor must be supplied");
    }

    @Test
    void rejectsMoreThanThreeFlavors() {
        assertThatThrownBy(() -> suggestionService.suggest(List.of(
                FlavorTag.SWEET,
                FlavorTag.BUTTERY,
                FlavorTag.FRUITY,
                FlavorTag.CARAMEL
        )))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("At most 3 flavors are supported");
    }

    @Test
    void returnsOnlyMatchingPastriesAndRanksByAverageThenOverlapThenName() {
        PastryEntity appelflap = pastry(PastryId.APPELFLAP, "Appelflap");
        PastryEntity croissant = pastry(PastryId.CROISSANT, "Croissant");
        PastryEntity stroopwafel = pastry(PastryId.STROOPWAFEL, "Stroopwafel");
        PastryEntity tompouce = pastry(PastryId.TOMPOUCE, "Tompouce");
        PastryEntity bagel = pastry(PastryId.BAGEL, "Bagel");

        when(pastryRepository.findAll()).thenReturn(List.of(appelflap, croissant, stroopwafel, tompouce, bagel));
        when(pastryStatsRepository.findAll()).thenReturn(List.of(
                stats(PastryId.APPELFLAP, 4.9, 12, "Apple tart"),
                stats(PastryId.CROISSANT, 4.8, 11, "Buttery and crisp"),
                stats(PastryId.STROOPWAFEL, 4.8, 9, "Caramel filled"),
                stats(PastryId.TOMPOUCE, 4.8, 8, "Custard layered")
        ));

        Map<String, List<PastryRatingEntity>> ratingsByPastry = Map.of(
                PastryId.APPELFLAP.name(), List.of(rating(PastryId.APPELFLAP, 5, Set.of(FlavorTag.FRUITY))),
                PastryId.CROISSANT.name(), List.of(rating(PastryId.CROISSANT, 5, EnumSet.of(FlavorTag.SWEET, FlavorTag.BUTTERY))),
                PastryId.STROOPWAFEL.name(), List.of(rating(PastryId.STROOPWAFEL, 5, EnumSet.of(FlavorTag.SWEET, FlavorTag.BUTTERY))),
                PastryId.TOMPOUCE.name(), List.of(rating(PastryId.TOMPOUCE, 5, Set.of(FlavorTag.SWEET))),
                PastryId.BAGEL.name(), List.of(rating(PastryId.BAGEL, 5, Set.of(FlavorTag.SALTY)))
        );
        when(pastryRatingRepository.findByPastryId(anyString())).thenAnswer(invocation ->
                ratingsByPastry.get(invocation.getArgument(0))
        );

        SuggestionResponse response = suggestionService.suggest(List.of(
                FlavorTag.SWEET,
                FlavorTag.BUTTERY,
                FlavorTag.FRUITY
        ));

        assertThat(response.suggestions())
                .extracting(PastrySuggestion::pastryName)
                .containsExactly("Appelflap", "Croissant", "Stroopwafel", "Tompouce");

        assertThat(response.suggestions())
                .extracting(PastrySuggestion::matchedFlavors)
                .containsExactly(
                        List.of(FlavorTag.FRUITY),
                        List.of(FlavorTag.SWEET, FlavorTag.BUTTERY),
                        List.of(FlavorTag.SWEET, FlavorTag.BUTTERY),
                        List.of(FlavorTag.SWEET)
                );
    }

    @Test
    void returnsZeroValuesWhenStatsAreMissing() {
        PastryEntity pastry = pastry(PastryId.CINNAMON_ROLL, "Cinnamon Roll");
        when(pastryRepository.findAll()).thenReturn(List.of(pastry));
        when(pastryStatsRepository.findAll()).thenReturn(List.of());
        when(pastryRatingRepository.findByPastryId(PastryId.CINNAMON_ROLL.name())).thenReturn(List.of(
                rating(PastryId.CINNAMON_ROLL, 4, EnumSet.of(FlavorTag.SWEET, FlavorTag.SPICED))
        ));

        SuggestionResponse response = suggestionService.suggest(List.of(FlavorTag.SWEET));

        assertThat(response.suggestions()).hasSize(1);
        assertThat(response.suggestions().get(0).averageRating()).isZero();
        assertThat(response.suggestions().get(0).ratingCount()).isZero();
        assertThat(response.suggestions().get(0).lastReviewDescription()).isNull();
    }

    private static PastryEntity pastry(PastryId pastryId, String displayName) {
        return new PastryEntity(pastryId.name(), displayName);
    }

    private static PastryRatingEntity rating(PastryId pastryId, int score, Set<FlavorTag> flavors) {
        PastryRatingEntity rating = new PastryRatingEntity();
        rating.setEventId(pastryId.name() + "-event");
        rating.setPastryId(pastryId.name());
        rating.setUserId("user-01");
        rating.setScore(score);
        rating.setExperience("Experience for " + pastryId.name());
        rating.setSubmittedAt(java.time.Instant.parse("2024-01-01T10:00:00Z"));
        rating.setFlavors(flavors);
        return rating;
    }

    private static PastryStatsEntity stats(PastryId pastryId, double averageScore, int ratingCount, String lastReviewExperience) {
        PastryStatsEntity stats = new PastryStatsEntity(pastryId.name());
        stats.setAverageScore(averageScore);
        stats.setRatingCount(ratingCount);
        stats.setLastReviewExperience(lastReviewExperience);
        stats.setLastReviewUserId("user-99");
        stats.setLastReviewScore(5);
        stats.setLastReviewFlavors("SWEET,BUTTERY");
        stats.setLastReviewSubmittedAt(java.time.Instant.parse("2024-01-01T12:00:00Z"));
        return stats;
    }
}
