package org.icescrum.web.config;

import org.icescrum.core.domain.User;
import org.icescrum.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("$2a$10$encodedpassword");
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEnabled(true);
        testUser.setAccountExpired(false);
        testUser.setAccountLocked(false);
        testUser.setPasswordExpired(false);
    }

    @Test
    void loadUserByUsername_existingUser_returnsUserDetails() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails details = userDetailsService.loadUserByUsername("testuser");

        assertNotNull(details);
        assertEquals("testuser", details.getUsername());
        assertEquals("$2a$10$encodedpassword", details.getPassword());
        assertTrue(details.isEnabled());
        assertEquals(1, details.getAuthorities().size());
        assertTrue(details.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_nonExistingUser_throwsException() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername("nonexistent")
        );
    }

    @Test
    void loadUserByUsername_disabledUser_returnsDisabledDetails() {
        testUser.setEnabled(false);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails details = userDetailsService.loadUserByUsername("testuser");

        assertFalse(details.isEnabled());
    }

    @Test
    void loadUserByUsername_accountExpired_returnsDisabledAccount() {
        testUser.setAccountExpired(true);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails details = userDetailsService.loadUserByUsername("testuser");

        assertFalse(details.isAccountNonExpired());
    }

    @Test
    void loadUserByUsername_passwordExpired_returnsDisabledPassword() {
        testUser.setPasswordExpired(true);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails details = userDetailsService.loadUserByUsername("testuser");

        assertFalse(details.isCredentialsNonExpired());
    }

    @Test
    void loadUserByUsername_accountLocked_returnsLockedAccount() {
        testUser.setAccountLocked(true);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails details = userDetailsService.loadUserByUsername("testuser");

        assertFalse(details.isAccountNonLocked());
    }

    @Test
    void loadUserByUsername_hasRoleUserAuthority() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDetails details = userDetailsService.loadUserByUsername("testuser");

        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> "ROLE_USER".equals(a.getAuthority())));
    }
}
