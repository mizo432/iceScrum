package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "is_activity")
public class Activity implements Serializable, Comparable<Activity> {

    public enum ActivityType {
        STORY_CREATE, STORY_UPDATE, STORY_DELETE,
        TASK_CREATE, TASK_UPDATE, TASK_DELETE,
        SPRINT_CREATE, SPRINT_UPDATE, SPRINT_DELETE,
        USER_CREATE, USER_UPDATE, USER_DELETE,
        COMMENT_CREATE, COMMENT_DELETE,
        ATTACHMENT_CREATE, ATTACHMENT_DELETE,
        CODE_SAVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String description;
    private int orderNumber = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private Instant dateCreated;

    public Activity() {
    }

    @PrePersist
    public void onPrePersist() {
        if (dateCreated == null) dateCreated = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getOrderNumber() { return orderNumber; }
    public void setOrderNumber(int orderNumber) { this.orderNumber = orderNumber; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public Instant getDateCreated() { return dateCreated; }
    public void setDateCreated(Instant dateCreated) { this.dateCreated = dateCreated; }

    @Override
    public int compareTo(Activity o) {
        if (o == null) return 1;
        int cmp = Integer.compare(this.orderNumber, o.orderNumber);
        if (cmp != 0) return cmp;
        return this.id.compareTo(o.id);
    }
}
