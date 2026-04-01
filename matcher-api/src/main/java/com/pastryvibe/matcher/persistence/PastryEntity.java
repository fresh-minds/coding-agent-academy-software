package com.pastryvibe.matcher.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pastry")
public class PastryEntity {

    @Id
    @Column(name = "pastry_id", nullable = false, length = 64)
    private String pastryId;

    @Column(name = "display_name", nullable = false, length = 120)
    private String displayName;

    public PastryEntity() {
    }

    public PastryEntity(String pastryId, String displayName) {
        this.pastryId = pastryId;
        this.displayName = displayName;
    }

    public String getPastryId() {
        return pastryId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
