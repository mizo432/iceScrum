package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "is_acceptance_test")
public class AcceptanceTest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private boolean passed = false;
    private int rank = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    private Instant dateCreated;
    private Instant lastUpdated;

    public AcceptanceTest() {
    }

    @PrePersist
    public void onPrePersist() {
        if (dateCreated == null) dateCreated = Instant.now();
        if (lastUpdated == null) lastUpdated = Instant.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        lastUpdated = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public Story getStory() { return story; }
    public void setStory(Story story) { this.story = story; }

    public Instant getDateCreated() { return dateCreated; }
    public void setDateCreated(Instant dateCreated) { this.dateCreated = dateCreated; }

    public Instant getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Instant lastUpdated) { this.lastUpdated = lastUpdated; }
}
