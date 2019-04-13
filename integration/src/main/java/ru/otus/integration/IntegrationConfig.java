package ru.otus.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import ru.otus.entity.Person;

import java.util.List;

@IntegrationComponentScan
@EnableIntegration
@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel saveInputChannel() {
        return MessageChannels.direct().datatype(Person.class).get();
    }

    @Bean
    public PublishSubscribeChannel saveOutputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public MessageChannel getInputChannel() {
        return MessageChannels.direct().datatype(String.class).get();
    }

    @Bean
    public PublishSubscribeChannel getOutputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public MessageChannel getAllInputChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public PublishSubscribeChannel getAllOutputChannel() {
        return MessageChannels.publishSubscribe().datatype(List.class).get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedDelay(1000).get();
    }

    @Bean
    public IntegrationFlow addPersonIntegrationFlow() {
        return IntegrationFlows.from("saveInputChannel")
                .handle("personServiceImpl", "save")
                .channel("saveOutputChannel")
                .get();
    }

    @Bean
    public IntegrationFlow getPersonIntegrationFlow() {
        return IntegrationFlows.from("getInputChannel")
                .handle("personServiceImpl", "get")
                .channel("getOutputChannel")
                .get();
    }

    @Bean
    public IntegrationFlow personIntegrationFlow() {
        return IntegrationFlows.from("getAllInputChannel")
                .handle("personServiceImpl", "getAll")
                .channel("getAllOutputChannel")
                .get();
    }
}