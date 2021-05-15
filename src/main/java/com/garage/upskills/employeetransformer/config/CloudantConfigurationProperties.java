package com.garage.upskills.employeetransformer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import java.net.URL;

@Data
@Profile("cloudant")
@ConfigurationProperties("cloudant")
public class CloudantConfigurationProperties {
    private URL url;
    private String username;
    private String password;
    private String db;
}
