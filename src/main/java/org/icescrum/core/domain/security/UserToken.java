package org.icescrum.core.domain.security;

import jakarta.persistence.*;
import org.icescrum.core.domain.User;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "is_user_token")
public class UserToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    private String name;
    private Instant expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Instant dateCreated;

    public UserToken() {
    }

    @PrePersist
    public void onPrePersist() {
        if (dateCreated == null) dateCreated = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Instant getDateCreated() { return dateCreated; }
    public void setDateCreated(Instant dateCreated) { this.dateCreated = dateCreated; }

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }
}
