package com.pastryvibe.matcher.service;

import com.pastryvibe.matcher.api.BestRatedPastryResponse;
import com.pastryvibe.matcher.persistence.PastryEntity;
import com.pastryvibe.matcher.persistence.PastryRepository;
import com.pastryvibe.matcher.persistence.PastryStatsEntity;
import com.pastryvibe.matcher.persistence.PastryStatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BestRatedPastryServiceTest {

    @Mock
    private PastryStatsRepository pastryStatsRepository;

    @Mock
    private PastryRepository pastryRepository;

    private BestRatedPastryService bestRatedPastryService;

    @BeforeEach
    void setUp() {
        bestRatedPastryService = new BestRatedPastryService(pastryStatsRepository, pastryRepository);
    }

    @Test
    void returnsBestRatedPastryFromPersistedMatcherData() {
        when(pastryRepository.findAll()).thenReturn(List.of(
                new PastryEntity("STROOPWAFEL", "Stroopwafel"),
                new PastryEntity("TOMPOUCE", "Tompouce")
        ));
        when(pastryStatsRepository.findAll()).thenReturn(List.of(
                stats("STROOPWAFEL", 4.7, 9, "Great texture and balanced sweetness."),
                stats("TOMPOUCE", 4.2, 12, "Solid pastry, especially with coffee.")
        ));

        BestRatedPastryResponse response = bestRatedPastryService.getBestRatedPastry();

        assertThat(response.pastryId().name()).isEqualTo("STROOPWAFEL");
        assertThat(response.pastryName()).isEqualTo("Stroopwafel");
        assertThat(response.averageRating()).isEqualTo(4.7);
        assertThat(response.ratingCount()).isEqualTo(9);
        assertThat(response.lastReviewDescription()).isEqualTo("Great texture and balanced sweetness.");
    }

    @Test
    void breaksAverageRatingTiesUsingRatingCount() {
        when(pastryRepository.findAll()).thenReturn(List.of(
                new PastryEntity("STROOPWAFEL", "Stroopwafel"),
                new PastryEntity("TOMPOUCE", "Tompouce")
        ));
        when(pastryStatsRepository.findAll()).thenReturn(List.of(
                stats("STROOPWAFEL", 4.5, 7, "A"),
                stats("TOMPOUCE", 4.5, 8, "B")
        ));

        BestRatedPastryResponse response = bestRatedPastryService.getBestRatedPastry();

        assertThat(response.pastryId().name()).isEqualTo("TOMPOUCE");
    }

    @Test
    void breaksRemainingTiesAlphabeticallyByPastryName() {
        when(pastryRepository.findAll()).thenReturn(List.of(
                new PastryEntity("STROOPWAFEL", "Stroopwafel"),
                new PastryEntity("TOMPOUCE", "Tompouce")
        ));
        when(pastryStatsRepository.findAll()).thenReturn(List.of(
                stats("STROOPWAFEL", 4.5, 8, "A"),
                stats("TOMPOUCE", 4.5, 8, "B")
        ));

        BestRatedPastryResponse response = bestRatedPastryService.getBestRatedPastry();

        assertThat(response.pastryName()).isEqualTo("Stroopwafel");
    }

    @Test
    void returnsNotFoundWhenNoRatedPastryExists() {
        when(pastryRepository.findAll()).thenReturn(List.of(new PastryEntity("STROOPWAFEL", "Stroopwafel")));
        when(pastryStatsRepository.findAll()).thenReturn(List.of());

        assertThatThrownBy(() -> bestRatedPastryService.getBestRatedPastry())
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.NOT_FOUND.toString())
                .hasMessageContaining("No rated pastry found.");
    }

    private PastryStatsEntity stats(String pastryId, double averageScore, int ratingCount, String lastReviewExperience) {
        PastryStatsEntity stats = new PastryStatsEntity(pastryId);
        stats.setAverageScore(averageScore);
        stats.setRatingCount(ratingCount);
        stats.setLastReviewExperience(lastReviewExperience);
        return stats;
    }
}
