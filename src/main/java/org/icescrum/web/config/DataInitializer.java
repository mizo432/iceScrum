package org.icescrum.web.config;

import org.icescrum.core.domain.security.Authority;
import org.icescrum.core.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                var user = new org.icescrum.core.domain.User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setEmail("admin@icescrum.org");
                user.setFirstName("Admin");
                user.setLastName("User");
                user.setUid(java.util.UUID.randomUUID().toString());
                user.setEnabled(true);
                userRepository.save(user);
                System.out.println("Initial admin user created: admin/admin");
            }
        };
    }
}
