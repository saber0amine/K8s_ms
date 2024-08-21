package com.dev.customerservice;

import com.dev.customerservice.entities.Customer;
import com.dev.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(CustomerRepository customerRepository , RepositoryRestConfiguration restConfiguration){
		return args -> {
			restConfiguration.exposeIdsFor(Customer.class);
			customerRepository.saveAll(List.of(
					Customer.builder().name("AMINE").email("AMINE@gmail.com").build(),
					Customer.builder().name("SABER").email("SABER@gmail.com").build(),
					Customer.builder().name("HAMID").email("HAMID@gmail.com").build()
			));
			customerRepository.findAll().forEach(c-> System.out.println(c.getId()));

		};
	}

}
