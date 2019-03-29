package ru.otus.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.dao.AclRepository;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.acls.mongodb.BasicLookupStrategy;
import org.springframework.security.acls.mongodb.MongoDBMutableAclService;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.net.UnknownHostException;

@Configuration
@EnableReactiveMethodSecurity
@EnableMongoRepositories(basePackageClasses = {AclRepository.class})
public class AclContextConfig {

    @Autowired
    public MongoTemplate mongoTemplate;

    @Autowired
    public AclRepository aclRepository;

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        ConsoleAuditLogger consoleAuditLogger = new ConsoleAuditLogger();
        return new DefaultPermissionGrantingStrategy(consoleAuditLogger);
    }

    @Bean
    public LookupStrategy lookupStrategy() throws UnknownHostException {
        return new BasicLookupStrategy(mongoTemplate, aclCache(), aclAuthorizationStrategy(), permissionGrantingStrategy());
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("aclCache");
    }

    @Bean
    public AclCache aclCache() {
        Cache springCache = cacheManager().getCache("aclCache");
        return new SpringCacheBasedAclCache(springCache, permissionGrantingStrategy(), aclAuthorizationStrategy());
    }

    @Bean
    public MongoDBMutableAclService aclService() throws UnknownHostException {
        return new MongoDBMutableAclService(aclRepository, lookupStrategy(), aclCache());
    }

    @Bean
    @Primary
    public MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() throws UnknownHostException {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(aclService());
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        expressionHandler.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService()));
        return expressionHandler;
    }
}