package ru.otus.controller;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;
import ru.otus.entity.Person;
import ru.otus.integration.PersonIntegration;

import java.util.List;

@RestController
public class PersonController {

    private final PersonIntegration service;

    public PersonController(PersonIntegration service) {
        this.service = service;
    }

    @PostMapping("/api/person")
    public Person save(@RequestBody Person person) {
        return service.save(person);
    }

    @GetMapping("/api/person/{id}")
    public Person get(@PathVariable("id") String id) {
        return service.get(id);
    }

    @GetMapping("/api/persons")
    public List<Person> getAll() {
        Message message = MessageBuilder.withPayload("").build();
        return service.getAll(message);
    }
}
