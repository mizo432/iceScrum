package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "is_release")
public class Release extends TimeBox implements Serializable {

    @Column(nullable = false)
    private String name;

    private int rank = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project parentProject;

    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Sprint> sprints = new ArrayList<>();

    @OneToMany(mappedBy = "release", fetch = FetchType.LAZY)
    private List<Story> stories = new ArrayList<>();

    public Release() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public Project getParentProject() { return parentProject; }
    public void setParentProject(Project parentProject) { this.parentProject = parentProject; }

    public List<Sprint> getSprints() { return sprints; }
    public void setSprints(List<Sprint> sprints) { this.sprints = sprints; }

    public List<Story> getStories() { return stories; }
    public void setStories(List<Story> stories) { this.stories = stories; }
}
