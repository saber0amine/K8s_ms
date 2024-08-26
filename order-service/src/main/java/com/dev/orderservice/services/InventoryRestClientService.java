package com.dev.orderservice.services;

 import com.dev.orderservice.models.Product;
 import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "k8s_inventory-service-app_inventory-service-app-865d94966-mffc8_default_c4d40d20-66d6-4cfc-b0b7-b2a09e036257_0")
public interface InventoryRestClientService {
    @GetMapping("/products/{id} ")
    public Product productById(@PathVariable Long id);
    @GetMapping("/products")
    public PagedModel<Product> allProducts();
}