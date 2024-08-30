package com.dev.orderservice.services;

import com.dev.orderservice.models.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//, url = "http://10.244.0.131:8081"

@FeignClient(name = "customer-service" , url = "http://10.244.1.101:8081")
public interface CustomerRestClientService {
//    @GetMapping("/customers/{id}")
//    public Customer customerById(@PathVariable Long id);
//    @GetMapping("/customers")
//    public PagedModel<Customer> allCustomers();

    @GetMapping("/customers/{id}?projection=fullCustomer")
    public Customer customerById(@PathVariable Long id);
    @GetMapping("/customers?projection=fullCustomer")
    public PagedModel<Customer> allCustomers();
}
