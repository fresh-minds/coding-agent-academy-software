package com.pastryvibe.matcher.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PastryRatingRepository extends JpaRepository<PastryRatingEntity, Long> {

    boolean existsByEventId(String eventId);

    List<PastryRatingEntity> findByPastryId(String pastryId);

    long countByPastryId(String pastryId);

    @Query("select avg(r.score) from PastryRatingEntity r where r.pastryId = :pastryId")
    Double findAverageScoreByPastryId(@Param("pastryId") String pastryId);
}
