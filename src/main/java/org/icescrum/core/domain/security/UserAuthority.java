package org.icescrum.core.domain.security;

import jakarta.persistence.*;
import org.icescrum.core.domain.User;

@Entity
@Table(name = "is_user_authority", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "authority_id"})
})
public class UserAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id", nullable = false)
    private Authority authority;

    public UserAuthority() {
    }

    public UserAuthority(User user, Authority authority) {
        this.user = user;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthority that = (UserAuthority) o;
        return user != null && that.user != null && user.getId().equals(that.user.getId())
             && authority != null && that.authority != null && authority.getId().equals(that.authority.getId());
    }

    @Override
    public int hashCode() {
        int result = 1;
        if (user != null) result = 31 * result + user.getId().hashCode();
        if (authority != null) result = 31 * result + authority.getId().hashCode();
        return result;
    }
}
