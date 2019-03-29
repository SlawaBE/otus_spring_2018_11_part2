package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.acls.domain.MongoAcl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public interface ReactiveAclRepository extends ReactiveMongoRepository<MongoAcl, Serializable> {

    Flux<MongoAcl> findByInstanceIdAndClassName(Serializable instanceId, String className);

    Mono<Long> deleteByInstanceId(Serializable instanceId);

}
