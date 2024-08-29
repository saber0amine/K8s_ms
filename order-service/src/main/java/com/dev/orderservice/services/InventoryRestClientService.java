package com.dev.orderservice.services;

 import com.dev.orderservice.models.Product;
 import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//url = "http://10.244.0.133:8082" for kubernetes
@FeignClient(name = "inventory-service" , url = "http://10.244.0.208:8082"  )
public interface InventoryRestClientService {
//    @GetMapping("/products/{id} ")
//    public Product productById(@PathVariable Long id);
//    @GetMapping("/products")
//    public PagedModel<Product> allProducts();

    @GetMapping("/products/{id}?projection=fullProduct")
    public Product productById(@PathVariable Long id);
    @GetMapping("/products?projection=fullProduct")
    public PagedModel<Product> allProducts();
}