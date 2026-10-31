package org.icescrum.core.domain;

import jakarta.persistence.*;
import org.icescrum.core.domain.preferences.ProjectPreferences;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "is_project", indexes = {
    @Index(name = "p_key_index", columnList = "pkey"),
    @Index(name = "p_name_index", columnList = "name")
})
public class Project extends TimeBox implements Serializable {

    public enum PlanningPokerGame {
        FIBO_SUITE, FIBO_SHORT, T_SHIRT, POINTS
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String pkey;

    private int planningPokerGameType = PlanningPokerGame.FIBO_SUITE.ordinal();

    private Integer attachmentsCount = 0;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "preferences_id")
    private ProjectPreferences preferences;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToMany
    @JoinTable(
        name = "is_project_team",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Actor> actors = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Backlog> backlogs = new ArrayList<>();

    @OneToMany(mappedBy = "parentProject", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Release> releases = new ArrayList<>();

    @OneToMany(mappedBy = "parentProject", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SimpleProjectApp> simpleProjectApps = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TimeBoxNotesTemplate> timeBoxNotesTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Widget> widgets = new ArrayList<>();

    @OneToMany(mappedBy = "parentProject", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Meeting> meetings = new ArrayList<>();

    public Project() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPkey() { return pkey; }
    public void setPkey(String pkey) { this.pkey = pkey; }

    public int getPlanningPokerGameType() { return planningPokerGameType; }
    public void setPlanningPokerGameType(int planningPokerGameType) { this.planningPokerGameType = planningPokerGameType; }

    public Integer getAttachmentsCount() { return attachmentsCount; }
    public void setAttachmentsCount(Integer attachmentsCount) { this.attachmentsCount = attachmentsCount; }

    public ProjectPreferences getPreferences() { return preferences; }
    public void setPreferences(ProjectPreferences preferences) { this.preferences = preferences; }

    public Portfolio getPortfolio() { return portfolio; }
    public void setPortfolio(Portfolio portfolio) { this.portfolio = portfolio; }

    public Set<Team> getTeams() { return teams; }
    public void setTeams(Set<Team> teams) { this.teams = teams; }

    public List<Actor> getActors() { return actors; }
    public void setActors(List<Actor> actors) { this.actors = actors; }


    public List<Release> getReleases() { return releases; }
    public void setReleases(List<Release> releases) { this.releases = releases; }

    public List<Backlog> getBacklogs() { return backlogs; }
    public void setBacklogs(List<Backlog> backlogs) { this.backlogs = backlogs; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public List<SimpleProjectApp> getSimpleProjectApps() { return simpleProjectApps; }
    public void setSimpleProjectApps(List<SimpleProjectApp> simpleProjectApps) { this.simpleProjectApps = simpleProjectApps; }

    public List<TimeBoxNotesTemplate> getTimeBoxNotesTemplates() { return timeBoxNotesTemplates; }
    public void setTimeBoxNotesTemplates(List<TimeBoxNotesTemplate> timeBoxNotesTemplates) { this.timeBoxNotesTemplates = timeBoxNotesTemplates; }

    public List<Widget> getWidgets() { return widgets; }
    public void setWidgets(List<Widget> widgets) { this.widgets = widgets; }

    public List<Meeting> getMeetings() { return meetings; }
    public void setMeetings(List<Meeting> meetings) { this.meetings = meetings; }

    public Team getTeam() {
        return teams.isEmpty() ? null : teams.iterator().next();
    }
}
