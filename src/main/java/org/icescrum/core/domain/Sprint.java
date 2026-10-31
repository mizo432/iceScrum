package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "is_sprint")
public class Sprint extends TimeBox implements Serializable {

    public enum SprintState {
        PLANNING, ONGOING, CLOSED, CANCELLED
    }

    private int rank = 0;
    private int burndownType = 0;
    private int state = SprintState.PLANNING.ordinal();
    private LocalDate closedDate;
    private LocalDate plannedEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "release_id")
    private Release release;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Story> stories = new ArrayList<>();

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();


    public Sprint() {
    }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public int getBurndownType() { return burndownType; }
    public void setBurndownType(int burndownType) { this.burndownType = burndownType; }

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

    public LocalDate getClosedDate() { return closedDate; }
    public void setClosedDate(LocalDate closedDate) { this.closedDate = closedDate; }

    public LocalDate getPlannedEndDate() { return plannedEndDate; }
    public void setPlannedEndDate(LocalDate plannedEndDate) { this.plannedEndDate = plannedEndDate; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public Release getRelease() { return release; }
    public void setRelease(Release release) { this.release = release; }

    public List<Story> getStories() { return stories; }
    public void setStories(List<Story> stories) { this.stories = stories; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }


    public SprintState getSprintState() {
        return SprintState.values()[state];
    }

    public void setSprintState(SprintState sprintState) {
        this.state = sprintState.ordinal();
    }
}
