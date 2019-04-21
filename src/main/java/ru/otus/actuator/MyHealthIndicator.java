package ru.otus.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.otus.repository.UserRepository;

@Component
public class MyHealthIndicator implements ReactiveHealthIndicator {

    private final UserRepository userRepository;

    public MyHealthIndicator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Health> health() {
        return userRepository.count().map(count -> count > 0 ?
                Health.up().build() :
                Health.down().withDetail("errorMessage", "Отсутствуют пользователи").build());
    }

}
