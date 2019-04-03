package ru.otus.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.entity.Role;
import ru.otus.entity.User;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class UserRepositoryTest extends RepositoryTest {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        Role adminRole = new Role("ROLE_ADMIN");
        mongoTemplate.insert(adminRole);

        User admin = new User("admin", "password");
        admin.setRoles(Collections.singletonList(adminRole));

        mongoTemplate.insert(admin);
    }

    @Test
    void testFindByLogin() {
        User user = userRepository.findByUsername("admin").block();
        assertThat(user)
                .hasFieldOrPropertyWithValue("username", "admin")
                .hasFieldOrPropertyWithValue("password", "password");
    }

}