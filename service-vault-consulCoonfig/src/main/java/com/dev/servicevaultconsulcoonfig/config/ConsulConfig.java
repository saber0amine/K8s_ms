package com.dev.servicevaultconsulcoonfig.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix ="token")
@Data
public class ConsulConfig {
private String Secret;
private String Timeout;
}
