package com.union.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jd")
public class JdConfig {

    private String serverUrl;

    private String appKey;

    private String appSecret;
}
