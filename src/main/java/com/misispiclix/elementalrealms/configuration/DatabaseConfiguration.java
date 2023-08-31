package com.misispiclix.elementalrealms.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
public class DatabaseConfiguration extends AbstractReactiveMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "sfg";
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    /*@Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.credential(MongoCredential.createCredential("root", "admin", "example".toCharArray()))
                .applyToClusterSettings(settings -> {
                    settings.hosts((singletonList(
                            new ServerAddress("127.0.0.1", 27017)
                    )));
                });
    }*/

}
