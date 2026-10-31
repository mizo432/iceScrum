package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "is_cliche")
public class Cliche implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project parentProject;

    public Cliche() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Project getParentProject() { return parentProject; }
    public void setParentProject(Project parentProject) { this.parentProject = parentProject; }
}
