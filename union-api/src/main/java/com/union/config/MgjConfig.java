package com.union.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mgj")
public class MgjConfig {

    private String appKey;

    private String appSecret;

    private String tokenUrl;

    private String redirectUri;

    private String serverUrl;


}
