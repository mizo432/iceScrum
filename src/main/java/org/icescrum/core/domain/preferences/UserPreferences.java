package org.icescrum.core.domain.preferences;

import jakarta.persistence.*;
import org.icescrum.core.domain.User;

import java.io.Serializable;

@Entity
@Table(name = "is_user_preferences")
public class UserPreferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locale;
    private String theme;
    private boolean emailNotifications = true;

    @OneToOne(mappedBy = "preferences", fetch = FetchType.LAZY)
    private User user;

    public UserPreferences() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLocale() { return locale; }
    public void setLocale(String locale) { this.locale = locale; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public boolean isEmailNotifications() { return emailNotifications; }
    public void setEmailNotifications(boolean emailNotifications) { this.emailNotifications = emailNotifications; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
