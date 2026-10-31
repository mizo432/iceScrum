package org.icescrum.core.service;

import org.icescrum.config.TestPostgresConfig;
import org.icescrum.core.domain.User;
import org.icescrum.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = org.icescrum.web.IceScrumApplication.class)
@Import(TestPostgresConfig.class)
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void create_userShouldBeSavedAndPasswordEncoded() {
        User user = userService.create("testuser", "password123", "test@example.com", "Test", "User");

        assertNotNull(user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertTrue(user.isEnabled());
        assertNotNull(user.getUid());
        assertTrue(passwordEncoder.matches("password123", user.getPassword()));
    }

    @Test
    void findByUsername_existingUser_returnsUser() {
        userService.create("findme", "pass", "find@example.com", "Find", "Me");

        Optional<User> found = userService.findByUsername("findme");

        assertTrue(found.isPresent());
        assertEquals("findme", found.get().getUsername());
    }

    @Test
    void findByUsername_nonExistingUser_returnsEmpty() {
        Optional<User> found = userService.findByUsername("nonexistent");

        assertTrue(found.isEmpty());
    }

    @Test
    void findByEmail_existingUser_returnsUser() {
        userService.create("emailuser", "pass", "email@example.com", "Email", "User");

        Optional<User> found = userService.findByEmail("email@example.com");

        assertTrue(found.isPresent());
        assertEquals("email@example.com", found.get().getEmail());
    }

    @Test
    void findByEmail_nonExistingUser_returnsEmpty() {
        Optional<User> found = userService.findByEmail("noone@example.com");

        assertTrue(found.isEmpty());
    }

    @Test
    void findAll_multipleUsers_returnsAll() {
        userService.create("user1", "pass", "u1@example.com", "User", "One");
        userService.create("user2", "pass", "u2@example.com", "User", "Two");

        List<User> users = userService.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void save_userShouldBePersisted() {
        userService.create("original", "pass", "orig@example.com", "Original", "User");
        Optional<User> opt = userService.findByUsername("original");
        assertTrue(opt.isPresent());

        User user = opt.get();
        user.setFirstName("Modified");
        User saved = userService.save(user);

        assertEquals("Modified", saved.getFirstName());
        assertEquals("Modified", userService.findByUsername("original").get().getFirstName());
    }

    @Test
    void delete_userShouldBeRemoved() {
        userService.create("todelete", "pass", "del@example.com", "To", "Delete");
        assertTrue(userService.findByUsername("todelete").isPresent());

        Optional<User> user = userService.findByUsername("todelete");
        assertTrue(user.isPresent());
        userService.delete(user.get().getId());

        assertTrue(userService.findByUsername("todelete").isEmpty());
    }

    @Test
    void create_passwordIsNotStoredInPlainText() {
        User user = userService.create("secure", "mysecret", "secure@example.com", "Secure", "User");

        assertNotEquals("mysecret", user.getPassword());
        assertTrue(passwordEncoder.matches("mysecret", user.getPassword()));
    }
}
