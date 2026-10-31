package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "is_task")
public class Task implements Serializable {

    public enum TaskState {
        TODO, IN_PROGRESS, DONE, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private int state = TaskState.TODO.ordinal();
    private int estimation = 0;
    private int remainingEstimation = 0;
    private int rank = 0;

    @Lob
    private String description;

    private LocalDate dueDate;
    private Instant dateCreated;
    private Instant lastUpdated;
    private String uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    private Story story;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project parentProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToMany
    @JoinTable(
        name = "is_task_user",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    public Task() {
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

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

    public int getEstimation() { return estimation; }
    public void setEstimation(int estimation) { this.estimation = estimation; }

    public int getRemainingEstimation() { return remainingEstimation; }
    public void setRemainingEstimation(int remainingEstimation) { this.remainingEstimation = remainingEstimation; }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

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

    public Story getStory() { return story; }
    public void setStory(Story story) { this.story = story; }

    public Sprint getSprint() { return sprint; }
    public void setSprint(Sprint sprint) { this.sprint = sprint; }

    public Project getParentProject() { return parentProject; }
    public void setParentProject(Project parentProject) { this.parentProject = parentProject; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public User getReporter() { return reporter; }
    public void setReporter(User reporter) { this.reporter = reporter; }

    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }

    public TaskState getTaskState() {
        return TaskState.values()[state];
    }

    public void setTaskState(TaskState taskState) {
        this.state = taskState.ordinal();
    }
}
