package org.icescrum.core.repository;

import org.icescrum.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUid(String uid);
    List<User> findByUsernameContainingIgnoreCase(String term);
    List<User> findByEmailContainingIgnoreCase(String term);
    List<User> findByFirstNameContainingIgnoreCase(String term);
    List<User> findByLastNameContainingIgnoreCase(String term);
}
