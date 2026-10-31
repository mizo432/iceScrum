package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "is_feature")
public class Feature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private int rank = 0;

    @Lob
    private String description;

    private Instant dateCreated;
    private Instant lastUpdated;
    private String uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "backlog_id", nullable = false)
    private Backlog backlog;

    @OneToMany(mappedBy = "feature", fetch = FetchType.LAZY)
    private List<Story> stories = new ArrayList<>();

    public Feature() {
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

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Instant getDateCreated() { return dateCreated; }
    public void setDateCreated(Instant dateCreated) { this.dateCreated = dateCreated; }

    public Instant getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Instant lastUpdated) { this.lastUpdated = lastUpdated; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public Backlog getBacklog() { return backlog; }
    public void setBacklog(Backlog backlog) { this.backlog = backlog; }

    public List<Story> getStories() { return stories; }
    public void setStories(List<Story> stories) { this.stories = stories; }
}
