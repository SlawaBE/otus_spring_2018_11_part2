package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.entity.Person;

public interface PersonRepository extends MongoRepository<Person, String> {
}
