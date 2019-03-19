package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.entity.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByUsername(String login);

}
