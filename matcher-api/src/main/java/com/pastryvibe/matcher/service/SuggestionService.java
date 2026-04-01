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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SuggestionService {

    private final PastryRepository pastryRepository;
    private final PastryRatingRepository pastryRatingRepository;
    private final PastryStatsRepository pastryStatsRepository;

    public SuggestionService(
            PastryRepository pastryRepository,
            PastryRatingRepository pastryRatingRepository,
            PastryStatsRepository pastryStatsRepository
    ) {
        this.pastryRepository = pastryRepository;
        this.pastryRatingRepository = pastryRatingRepository;
        this.pastryStatsRepository = pastryStatsRepository;
    }

    public SuggestionResponse suggest(List<FlavorTag> requestedFlavors) {
        if (requestedFlavors == null || requestedFlavors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one flavor must be supplied.");
        }
        if (requestedFlavors.size() > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At most 3 flavors are supported.");
        }

        Set<FlavorTag> requestSet = EnumSet.copyOf(requestedFlavors);
        Map<String, PastryStatsEntity> statsByPastry = pastryStatsRepository.findAll().stream()
                .collect(Collectors.toMap(PastryStatsEntity::getPastryId, value -> value));

        List<PastrySuggestion> suggestions = pastryRepository.findAll().stream()
                .map(pastry -> buildSuggestion(pastry, requestSet, statsByPastry.get(pastry.getPastryId())))
                .filter(candidate -> candidate.overlappingFlavorCount() > 0)
                .sorted(
                        Comparator.comparingDouble(PastrySuggestion::averageRating).reversed()
                                .thenComparingInt(PastrySuggestion::overlappingFlavorCount).reversed()
                                .thenComparing(PastrySuggestion::pastryName)
                )
                .toList();

        return new SuggestionResponse(suggestions);
    }

    private PastrySuggestion buildSuggestion(PastryEntity pastry, Set<FlavorTag> requestSet, PastryStatsEntity stats) {
        Set<FlavorTag> pastryFlavors = pastryRatingRepository.findByPastryId(pastry.getPastryId()).stream()
                .map(PastryRatingEntity::getFlavors)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        List<FlavorTag> matched = requestSet.stream()
                .filter(pastryFlavors::contains)
                .sorted(Enum::compareTo)
                .toList();

        return new PastrySuggestion(
                PastryId.valueOf(pastry.getPastryId()),
                pastry.getDisplayName(),
                stats == null ? 0.0 : stats.getAverageScore(),
                stats == null ? 0 : stats.getRatingCount(),
                matched.size(),
                matched
        );
    }
}
