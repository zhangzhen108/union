package com.union.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "tb")
public class TbConfig {
    private String appkey;

    private String secret;

    private String serverUrl;
}
