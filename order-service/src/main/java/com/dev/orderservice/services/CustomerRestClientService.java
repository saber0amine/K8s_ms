package com.dev.orderservice.services;

import com.dev.orderservice.models.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "customer-service-app-54d8f98f4c-vp64p")
public interface CustomerRestClientService {
    @GetMapping("/customers/{id}")
    public Customer customerById(@PathVariable Long id);
    @GetMapping("/customers")
    public PagedModel<Customer> allCustomers();
}
