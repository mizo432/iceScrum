package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "is_simple_project_app")
public class SimpleProjectApp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public SimpleProjectApp() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
}
