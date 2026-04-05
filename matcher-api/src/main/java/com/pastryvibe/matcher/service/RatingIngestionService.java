package com.pastryvibe.matcher.service;

import com.pastryvibe.matcher.domain.RatingEvent;
import com.pastryvibe.matcher.persistence.PastryEntity;
import com.pastryvibe.matcher.persistence.PastryRatingEntity;
import com.pastryvibe.matcher.persistence.PastryRatingRepository;
import com.pastryvibe.matcher.persistence.PastryRepository;
import com.pastryvibe.matcher.persistence.PastryStatsEntity;
import com.pastryvibe.matcher.persistence.PastryStatsRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RatingIngestionService {

    private final PastryNameFormatter pastryNameFormatter = new PastryNameFormatter();
    private final PastryRepository pastryRepository;
    private final PastryRatingRepository pastryRatingRepository;
    private final PastryStatsRepository pastryStatsRepository;

    public RatingIngestionService(
            PastryRepository pastryRepository,
            PastryRatingRepository pastryRatingRepository,
            PastryStatsRepository pastryStatsRepository
    ) {
        this.pastryRepository = pastryRepository;
        this.pastryRatingRepository = pastryRatingRepository;
        this.pastryStatsRepository = pastryStatsRepository;
    }

    @KafkaListener(topics = "${app.kafka.topics.ratings}")
    @Transactional
    public void onRatingEvent(RatingEvent event) {
        if (pastryRatingRepository.existsByEventId(event.eventId().toString())) {
            return;
        }

        pastryRepository.findById(event.pastryId().name())
                .orElseGet(() -> pastryRepository.save(new PastryEntity(
                        event.pastryId().name(),
                        pastryNameFormatter.humanize(event.pastryId().name())
                )));

        PastryRatingEntity rating = new PastryRatingEntity();
        rating.setEventId(event.eventId().toString());
        rating.setPastryId(event.pastryId().name());
        rating.setUserId(event.userId());
        rating.setScore(event.score());
        rating.setExperience(event.experience());
        rating.setSubmittedAt(event.submittedAt());
        rating.setFlavors(Set.copyOf(event.flavors()));
        pastryRatingRepository.save(rating);

        refreshStatsFor(event.pastryId().name(), event);
    }

    private void refreshStatsFor(String pastryId, RatingEvent incomingEvent) {
        long ratingCount = pastryRatingRepository.countByPastryId(pastryId);
        double averageScore = pastryRatingRepository.findAverageScoreByPastryId(pastryId) == null
                ? 0
                : pastryRatingRepository.findAverageScoreByPastryId(pastryId);

        PastryStatsEntity stats = pastryStatsRepository.findById(pastryId).orElse(new PastryStatsEntity(pastryId));
        stats.setRatingCount((int) ratingCount);
        stats.setAverageScore(averageScore);

        // Intentionally susceptible to out-of-order events for Exercise 01.
        stats.setLastReviewSubmittedAt(incomingEvent.submittedAt());
        stats.setLastReviewUserId(incomingEvent.userId());
        stats.setLastReviewScore(incomingEvent.score());
        stats.setLastReviewExperience(incomingEvent.experience());
        stats.setLastReviewFlavors(incomingEvent.flavors().stream().map(Enum::name).collect(Collectors.joining(",")));

        pastryStatsRepository.save(stats);
    }
}
