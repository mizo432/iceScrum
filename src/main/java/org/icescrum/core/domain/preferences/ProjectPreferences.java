package org.icescrum.core.domain.preferences;

import jakarta.persistence.*;
import org.icescrum.core.domain.Project;

import java.io.Serializable;

@Entity
@Table(name = "is_project_preferences")
public class ProjectPreferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String theme;
    private String defaultBacklog;

    @OneToOne(mappedBy = "preferences", fetch = FetchType.LAZY)
    private Project project;

    public ProjectPreferences() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String getDefaultBacklog() { return defaultBacklog; }
    public void setDefaultBacklog(String defaultBacklog) { this.defaultBacklog = defaultBacklog; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
}
