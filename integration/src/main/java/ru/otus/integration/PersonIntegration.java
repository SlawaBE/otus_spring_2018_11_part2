package ru.otus.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import ru.otus.entity.Person;

import java.util.List;

@MessagingGateway
public interface PersonIntegration {

    @Gateway(requestChannel = "saveInputChannel", replyChannel = "saveOutputChannel")
    Person save(Person person);

    @Gateway(requestChannel = "getInputChannel", replyChannel = "getOutputChannel")
    Person get(String id);

    @Gateway(requestChannel = "getAllInputChannel", replyChannel = "getAllOutputChannel")
    List<Person> getAll(Message o);
}
