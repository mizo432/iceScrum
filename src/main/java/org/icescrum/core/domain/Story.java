package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "is_story")
public class Story implements Serializable {

    public enum StoryState {
        TODO, IN_PROGRESS, DONE, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private int rank = 0;
    private int state = StoryState.TODO.ordinal();
    private int estimation = 0;
    private int storyPoints = 0;
    private int remainingEstimation = 0;

    @Lob
    private String description;

    private LocalDate dueDate;
    private Instant dateCreated;
    private Instant lastUpdated;
    private String uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "backlog_id", nullable = false)
    private Backlog backlog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Story parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private Actor actor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "release_id")
    private Release release;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Story> children = new ArrayList<>();

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AcceptanceTest> acceptanceTests = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "is_story_cliche",
        joinColumns = @JoinColumn(name = "story_id"),
        inverseJoinColumns = @JoinColumn(name = "cliche_id")
    )
    private Set<Cliche> cliches = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "is_story_user",
        joinColumns = @JoinColumn(name = "story_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    public Story() {
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

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

    public int getEstimation() { return estimation; }
    public void setEstimation(int estimation) { this.estimation = estimation; }

    public int getStoryPoints() { return storyPoints; }
    public void setStoryPoints(int storyPoints) { this.storyPoints = storyPoints; }

    public int getRemainingEstimation() { return remainingEstimation; }
    public void setRemainingEstimation(int remainingEstimation) { this.remainingEstimation = remainingEstimation; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Instant getDateCreated() { return dateCreated; }
    public void setDateCreated(Instant dateCreated) { this.dateCreated = dateCreated; }

    public Instant getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Instant lastUpdated) { this.lastUpdated = lastUpdated; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public Backlog getBacklog() { return backlog; }
    public void setBacklog(Backlog backlog) { this.backlog = backlog; }

    public Sprint getSprint() { return sprint; }
    public void setSprint(Sprint sprint) { this.sprint = sprint; }

    public Story getParent() { return parent; }
    public void setParent(Story parent) { this.parent = parent; }

    public Actor getActor() { return actor; }
    public void setActor(Actor actor) { this.actor = actor; }

    public Feature getFeature() { return feature; }
    public void setFeature(Feature feature) { this.feature = feature; }

    public Release getRelease() { return release; }
    public void setRelease(Release release) { this.release = release; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public User getReporter() { return reporter; }
    public void setReporter(User reporter) { this.reporter = reporter; }

    public List<Story> getChildren() { return children; }
    public void setChildren(List<Story> children) { this.children = children; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public List<AcceptanceTest> getAcceptanceTests() { return acceptanceTests; }
    public void setAcceptanceTests(List<AcceptanceTest> acceptanceTests) { this.acceptanceTests = acceptanceTests; }

    public Set<Cliche> getCliches() { return cliches; }
    public void setCliches(Set<Cliche> cliches) { this.cliches = cliches; }

    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }

    public StoryState getStoryState() {
        return StoryState.values()[state];
    }

    public void setStoryState(StoryState storyState) {
        this.state = storyState.ordinal();
    }
}
