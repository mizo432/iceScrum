package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "is_meeting")
public class Meeting extends TimeBox implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project parentProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint parentSprint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id_meeting")
    private Sprint parentSprintOnly;

    public Meeting() {
    }

    public Project getParentProject() { return parentProject; }
    public void setParentProject(Project parentProject) { this.parentProject = parentProject; }

    public Sprint getParentSprint() { return parentSprint; }
    public void setParentSprint(Sprint parentSprint) { this.parentSprint = parentSprint; }

    public Sprint getParentSprintOnly() { return parentSprintOnly; }
    public void setParentSprintOnly(Sprint parentSprintOnly) { this.parentSprintOnly = parentSprintOnly; }
}
