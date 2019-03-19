package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;

@Service
public class UserServiceImpl implements ReactiveUserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        Mono<User> mono = repository.findByUsername(s);
        return mono.switchIfEmpty(Mono.empty()).cast(UserDetails.class);
    }
}
