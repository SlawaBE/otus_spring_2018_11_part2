package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.entity.Person;
import ru.otus.repository.PersonRepository;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Person> getAll() {
        return repository.findAll();
    }

    @Override
    public Person save(Person person) {
        return repository.save(person);
    }

    @Override
    public Person get(String id) {
        return repository.findById(id).get();
    }
}