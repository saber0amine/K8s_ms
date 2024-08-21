package com.dev.servicevaultconsulcoonfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.Versioned;

import java.util.Map;

@SpringBootApplication
public class ServiceVaultConsulCoonfigApplication {


	@Autowired
	private VaultTemplate vaultTemplate;


	public static void main(String[] args) {
		SpringApplication.run(ServiceVaultConsulCoonfigApplication.class, args);
	}

	@Bean
	CommandLineRunner start(VaultTemplate vaultTemplate) {
		return args -> {
			Versioned.Metadata res = vaultTemplate.opsForVersionedKeyValue("secret")
					.put("vaultmicroservice", Map.of("vault.username" ,"AMINE" ,  "vault.password", "hahah") );
			System.out.println("*******************");
			System.out.println(res);
			System.out.println("*******************");
			System.out.println("*******************");
			System.out.println(vaultTemplate.opsForSys().health());
			System.out.println("*******************");
		};
	}

}
