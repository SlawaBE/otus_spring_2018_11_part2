package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;
import ru.otus.entity.Role;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Import(UserServiceImpl.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void findByUsername() {
        when(userRepository.findByUsername("admin")).thenReturn(Mono.fromSupplier(this::getUser));
        UserDetails user = userService.findByUsername("admin").block();
        assertThat(user)
                .hasFieldOrPropertyWithValue("username", "admin")
                .hasFieldOrPropertyWithValue("password", "password");
    }

    private User getUser() {
        User user = new User("admin", "password");
        user.setRoles(Collections.singletonList(new Role("ROLE_ADMIN")));
        return user;
    }
}