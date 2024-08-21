package com.dev.servicevaultconsulcoonfig.web;

import com.dev.servicevaultconsulcoonfig.config.ConsulConfig;
import com.dev.servicevaultconsulcoonfig.config.VaultConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RefreshScope
public class SecretsRest {

    @Autowired
    private ConsulConfig consulConfig;
    @Autowired
    private VaultConfig vaultConfig ;
    //    @Value("${token.Secret}")
//    private String tokenSecret;
//    @Value("${token.Timeout}")
//    private  String TokenTimeOut ;

@GetMapping("/secrets")
    public Map<String ,  Object > params(){
        return Map.of("Secret:", consulConfig.getSecret() ,  "TimeOut:", consulConfig.getTimeout()    , "vaultSec" , vaultConfig);
    }


}
