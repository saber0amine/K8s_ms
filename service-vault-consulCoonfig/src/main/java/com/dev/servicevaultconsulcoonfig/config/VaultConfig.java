package com.dev.servicevaultconsulcoonfig.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
 import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "vault")
public class VaultConfig {
    private  String username;
    private  String password;

}
