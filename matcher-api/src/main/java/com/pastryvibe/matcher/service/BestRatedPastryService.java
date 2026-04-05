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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BestRatedPastryService {

    private final PastryStatsRepository pastryStatsRepository;
    private final PastryRepository pastryRepository;

    public BestRatedPastryService(PastryStatsRepository pastryStatsRepository, PastryRepository pastryRepository) {
        this.pastryStatsRepository = pastryStatsRepository;
        this.pastryRepository = pastryRepository;
    }

    public BestRatedPastryResponse getBestRatedPastry() {
        List<PastryStatsEntity> stats = pastryStatsRepository.findAll();
        List<PastryEntity> pastries = pastryRepository.findAll();
        List<BestRatedPastryResponse> bestRatedCandidates = new ArrayList<>();

        for (PastryStatsEntity pastryStats : stats) {
            PastryEntity pastry = null;
            for (PastryEntity candidate : pastries) {
                if (candidate.getPastryId().equals(pastryStats.getPastryId())) {
                    pastry = candidate;
                    break;
                }
            }

            if (pastry != null) {
                bestRatedCandidates.add(new BestRatedPastryResponse(
                        PastryId.valueOf(pastryStats.getPastryId()),
                        pastry.getDisplayName(),
                        pastryStats.getAverageScore(),
                        pastryStats.getRatingCount(),
                        pastryStats.getLastReviewExperience()
                ));
            }
        }

        return bestRatedCandidates.stream()
                .sorted(
                        Comparator.comparingDouble(BestRatedPastryResponse::averageRating).reversed()
                                .thenComparing(Comparator.comparingInt(BestRatedPastryResponse::ratingCount).reversed())
                                .thenComparing(BestRatedPastryResponse::pastryName)
                )
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No rated pastry found."));
    }
}
