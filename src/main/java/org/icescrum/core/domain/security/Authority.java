package org.icescrum.core.domain.security;

import jakarta.persistence.*;

@Entity
@Table(name = "is_authority")
public class Authority {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_VISITOR = "ROLE_ANONYMOUS";
    public static final String ROLE_PERMISSION = "ROLE_RUN_AS_PERMISSIONS_MANAGER";

    public static final int MEMBER = 0;
    public static final int SCRUMMASTER = 1;
    public static final int PRODUCTOWNER = 2;
    public static final int STAKEHOLDER = 3;
    public static final int PO_AND_SM = 4;
    public static final int BUSINESSOWNER = 5;
    public static final int PORTFOLIOSTAKEHOLDER = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String authority;

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public static String getAuthorityString(String authority, Long teamId, boolean isTeam) {
        return isTeam ? authority + "_T" + teamId : authority + "_P" + teamId;
    }
}
