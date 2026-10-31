package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "is_team")
public class Team implements Serializable, Comparable<Team> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private int velocity = 0;

    @Column(length = 1000)
    private String description;

    private String uid;
    private Instant dateCreated;
    private Instant lastUpdated;

    @ManyToMany
    @JoinTable(
        name = "is_user_team",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    @ManyToMany(mappedBy = "teams")
    private List<Project> projects = new ArrayList<>();

    public Team() {
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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getVelocity() { return velocity; }
    public void setVelocity(int velocity) { this.velocity = velocity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public Instant getDateCreated() { return dateCreated; }
    public void setDateCreated(Instant dateCreated) { this.dateCreated = dateCreated; }

    public Instant getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Instant lastUpdated) { this.lastUpdated = lastUpdated; }

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }

    public List<Project> getProjects() { return projects; }
    public void setProjects(List<Project> projects) { this.projects = projects; }

    @Override
    public int compareTo(Team o) {
        if (o == null) return 1;
        int cmp = this.name.compareTo(o.name);
        if (cmp != 0) return cmp;
        return this.id.compareTo(o.id);
    }
}
