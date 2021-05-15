package com.garage.upskills.employeetransformer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloudant")
@EnableConfigurationProperties(CloudantConfigurationProperties.class)
public class CloudantConfiguration {

    @Autowired
    private CloudantConfigurationProperties cloudantConfig;

    @Bean
    public CloudantClient cloudantClient() {
        return ClientBuilder
                .url(cloudantConfig.getUrl())
                .username(cloudantConfig.getUsername())
                .password(cloudantConfig.getPassword())
                .build();
    }

    @Bean
    public Database database(CloudantClient client) {
        return client.database(cloudantConfig.getDb(), true);
    }

}
