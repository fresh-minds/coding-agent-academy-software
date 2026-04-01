package com.pastryvibe.matcher.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PastryRepository extends JpaRepository<PastryEntity, String> {
}
