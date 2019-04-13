package ru.otus.service;

import ru.otus.entity.Person;

import java.util.List;

public interface PersonService {

    Person save(Person person);

    Person get(String id);

    List<Person> getAll();
}
