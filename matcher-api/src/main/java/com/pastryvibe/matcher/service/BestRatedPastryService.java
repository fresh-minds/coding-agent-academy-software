package com.pastryvibe.matcher.service;

import com.pastryvibe.matcher.api.BestRatedPastryResponse;
import com.pastryvibe.matcher.domain.PastryId;
import com.pastryvibe.matcher.persistence.PastryEntity;
import com.pastryvibe.matcher.persistence.PastryRepository;
import com.pastryvibe.matcher.persistence.PastryStatsEntity;
import com.pastryvibe.matcher.persistence.PastryStatsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BestRatedPastryService {

    private final PastryStatsRepository pastryStatsRepository;
    private final PastryRepository pastryRepository;

    public BestRatedPastryService(
            PastryStatsRepository pastryStatsRepository,
            PastryRepository pastryRepository
    ) {
        this.pastryStatsRepository = pastryStatsRepository;
        this.pastryRepository = pastryRepository;
    }

    public BestRatedPastryResponse getBestRatedPastry() {
        Map<String, PastryEntity> pastryById = pastryRepository.findAll().stream()
                .collect(Collectors.toMap(PastryEntity::getPastryId, pastry -> pastry));

        return pastryStatsRepository.findAll().stream()
                .map(stats -> toResponse(stats, pastryById.get(stats.getPastryId())))
                .filter(response -> response != null)
                .sorted(
                        Comparator.comparingDouble(BestRatedPastryResponse::averageRating).reversed()
                                .thenComparing(Comparator.comparingInt(BestRatedPastryResponse::ratingCount).reversed())
                                .thenComparing(BestRatedPastryResponse::pastryName)
                )
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No rated pastry found."));
    }

    private BestRatedPastryResponse toResponse(PastryStatsEntity stats, PastryEntity pastry) {
        if (pastry == null) {
            return null;
        }

        return new BestRatedPastryResponse(
                PastryId.valueOf(stats.getPastryId()),
                pastry.getDisplayName(),
                stats.getAverageScore(),
                stats.getRatingCount(),
                stats.getLastReviewExperience()
        );
    }
}
