package com.pastryvibe.matcher.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "pastry_stats")
public class PastryStatsEntity {

    @Id
    @Column(name = "pastry_id", nullable = false, length = 64)
    private String pastryId;

    @Column(name = "average_score", nullable = false)
    private double averageScore;

    @Column(name = "rating_count", nullable = false)
    private int ratingCount;

    @Column(name = "last_review_submitted_at")
    private Instant lastReviewSubmittedAt;

    @Column(name = "last_review_user_id", length = 120)
    private String lastReviewUserId;

    @Column(name = "last_review_score")
    private Integer lastReviewScore;

    @Column(name = "last_review_experience", length = 2000)
    private String lastReviewExperience;

    @Column(name = "last_review_flavors", length = 600)
    private String lastReviewFlavors;

    public PastryStatsEntity() {
    }

    public PastryStatsEntity(String pastryId) {
        this.pastryId = pastryId;
    }

    public String getPastryId() {
        return pastryId;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Instant getLastReviewSubmittedAt() {
        return lastReviewSubmittedAt;
    }

    public void setLastReviewSubmittedAt(Instant lastReviewSubmittedAt) {
        this.lastReviewSubmittedAt = lastReviewSubmittedAt;
    }

    public String getLastReviewUserId() {
        return lastReviewUserId;
    }

    public void setLastReviewUserId(String lastReviewUserId) {
        this.lastReviewUserId = lastReviewUserId;
    }

    public Integer getLastReviewScore() {
        return lastReviewScore;
    }

    public void setLastReviewScore(Integer lastReviewScore) {
        this.lastReviewScore = lastReviewScore;
    }

    public String getLastReviewExperience() {
        return lastReviewExperience;
    }

    public void setLastReviewExperience(String lastReviewExperience) {
        this.lastReviewExperience = lastReviewExperience;
    }

    public String getLastReviewFlavors() {
        return lastReviewFlavors;
    }

    public void setLastReviewFlavors(String lastReviewFlavors) {
        this.lastReviewFlavors = lastReviewFlavors;
    }
}
