package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "is_portfolio")
public class Portfolio extends TimeBox implements Serializable {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String pkey;

    private String uid;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Project> projects = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "is_portfolio_team",
        joinColumns = @JoinColumn(name = "portfolio_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new LinkedHashSet<>();

    public Portfolio() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPkey() { return pkey; }
    public void setPkey(String pkey) { this.pkey = pkey; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public List<Project> getProjects() { return projects; }
    public void setProjects(List<Project> projects) { this.projects = projects; }

    public Set<Team> getTeams() { return teams; }
    public void setTeams(Set<Team> teams) { this.teams = teams; }
}
