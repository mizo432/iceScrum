package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "is_invitation")
public class Invitation implements Serializable {

    public enum InvitationType {
        MEMBER, SCRUMMASTER, PRODUCTOWNER, STAKEHOLDER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private int type = InvitationType.MEMBER.ordinal();
    private String token;
    private boolean used = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Instant dateCreated;
    private Instant lastUpdated;

    public Invitation() {
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

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Instant getDateCreated() { return dateCreated; }
    public void setDateCreated(Instant dateCreated) { this.dateCreated = dateCreated; }

    public Instant getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Instant lastUpdated) { this.lastUpdated = lastUpdated; }

    public InvitationType getInvitationType() {
        return InvitationType.values()[type];
    }

    public void setInvitationType(InvitationType invitationType) {
        this.type = invitationType.ordinal();
    }
}
