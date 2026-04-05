package com.pastryvibe.matcher.service;

import com.pastryvibe.matcher.domain.FlavorTag;
import com.pastryvibe.matcher.domain.PastryId;
import com.pastryvibe.matcher.persistence.PastryEntity;
import com.pastryvibe.matcher.persistence.PastryRatingEntity;
import com.pastryvibe.matcher.persistence.PastryRatingRepository;
import com.pastryvibe.matcher.persistence.PastryRepository;
import com.pastryvibe.matcher.persistence.PastryStatsEntity;
import com.pastryvibe.matcher.persistence.PastryStatsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BaselineDataSeeder implements CommandLineRunner {

    private final PastryNameFormatter pastryNameFormatter = new PastryNameFormatter();
    private final PastryRepository pastryRepository;
    private final PastryRatingRepository pastryRatingRepository;
    private final PastryStatsRepository pastryStatsRepository;

    public BaselineDataSeeder(
            PastryRepository pastryRepository,
            PastryRatingRepository pastryRatingRepository,
            PastryStatsRepository pastryStatsRepository
    ) {
        this.pastryRepository = pastryRepository;
        this.pastryRatingRepository = pastryRatingRepository;
        this.pastryStatsRepository = pastryStatsRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (pastryRepository.count() > 0) {
            return;
        }

        Random random = new Random(42);
        List<String> users = new ArrayList<>();
        for (int index = 1; index <= 15; index++) {
            users.add(String.format("user-%02d", index));
        }

        List<String> comments = List.of(
                "Great texture and balanced sweetness.",
                "Very comforting, would order again.",
                "A little too rich for breakfast but still nice.",
                "Solid pastry, especially with coffee.",
                "Unexpectedly good flavor combination.",
                "Crisp outside, soft middle, proper joy."
        );

        Instant cursor = Instant.now().minus(14, ChronoUnit.DAYS);
        for (PastryId pastryId : PastryId.values()) {
            pastryRepository.save(new PastryEntity(
                    pastryId.name(),
                    pastryNameFormatter.humanize(pastryId.name())
            ));

            int ratingCount = 1 + random.nextInt(10);
            for (int i = 0; i < ratingCount; i++) {
                PastryRatingEntity rating = new PastryRatingEntity();
                rating.setEventId(UUID.randomUUID().toString());
                rating.setPastryId(pastryId.name());
                rating.setUserId(users.get(random.nextInt(users.size())));
                rating.setScore(1 + random.nextInt(5));
                rating.setExperience(comments.get(random.nextInt(comments.size())));
                rating.setSubmittedAt(cursor.plus(2, ChronoUnit.HOURS));
                rating.setFlavors(randomFlavors(random));
                pastryRatingRepository.save(rating);
                cursor = cursor.plus(2, ChronoUnit.HOURS);
            }
        }

        for (PastryId pastryId : PastryId.values()) {
            recomputeStats(pastryId.name());
        }
    }

    private Set<FlavorTag> randomFlavors(Random random) {
        FlavorTag[] allFlavors = FlavorTag.values();
        int pickCount = 1 + random.nextInt(3);
        Set<FlavorTag> picked = EnumSet.noneOf(FlavorTag.class);
        while (picked.size() < pickCount) {
            picked.add(allFlavors[random.nextInt(allFlavors.length)]);
        }
        return picked;
    }

    private void recomputeStats(String pastryId) {
        List<PastryRatingEntity> ratings = pastryRatingRepository.findByPastryId(pastryId);
        if (ratings.isEmpty()) {
            return;
        }

        double average = ratings.stream().mapToInt(PastryRatingEntity::getScore).average().orElse(0);
        PastryRatingEntity lastReview = ratings.stream()
                .max(Comparator.comparing(PastryRatingEntity::getSubmittedAt))
                .orElseThrow();

        PastryStatsEntity stats = new PastryStatsEntity(pastryId);
        stats.setAverageScore(average);
        stats.setRatingCount(ratings.size());
        stats.setLastReviewSubmittedAt(lastReview.getSubmittedAt());
        stats.setLastReviewUserId(lastReview.getUserId());
        stats.setLastReviewScore(lastReview.getScore());
        stats.setLastReviewExperience(lastReview.getExperience());
        stats.setLastReviewFlavors(lastReview.getFlavors().stream().map(Enum::name).collect(Collectors.joining(",")));
        pastryStatsRepository.save(stats);
    }
}
