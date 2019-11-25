package com.union.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration
@ConfigurationProperties(prefix = "pdd")
public class PddConfig {

    private String clientId;

    private String clientSecret;
}
