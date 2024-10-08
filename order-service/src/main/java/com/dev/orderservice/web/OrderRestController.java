package com.dev.orderservice.web;

import com.dev.orderservice.entities.Order;
import com.dev.orderservice.entities.ProductItem;
import com.dev.orderservice.enums.OrderStatus;
import com.dev.orderservice.models.Customer;
import com.dev.orderservice.models.Product;
import com.dev.orderservice.repositories.OrderRepository;
import com.dev.orderservice.repositories.ProductItemRepository;
import com.dev.orderservice.services.CustomerRestClientService;
import com.dev.orderservice.services.InventoryRestClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
public class OrderRestController {
    private OrderRepository orderRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClientService customerRestClientService;
    private InventoryRestClientService inventoryRestClientService;

    public OrderRestController(OrderRepository orderRepository, ProductItemRepository productItemRepository, CustomerRestClientService customerRestClientService, InventoryRestClientService inventoryRestClientService) {
        this.orderRepository = orderRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClientService = customerRestClientService;
        this.inventoryRestClientService = inventoryRestClientService;
    }


    //
    @GetMapping("/orders/fullOrder/{id}")
    public Order getOrder(@PathVariable Long id){
        Order order=orderRepository.findById(id).get();
        Customer customer=customerRestClientService.customerById(order.getCustomerId());
        order.setCustomer(customer);
        order.getProductItems().forEach(pi->{
            Product product=inventoryRestClientService.productById(pi.getProductId());
            pi.setProduct(product);
        });
        return order;
    }



    @GetMapping("/orders")
    public List<Order> orders(){
        List<Customer> customers=customerRestClientService.allCustomers().getContent().stream().toList();
        System.out.println("customers: "+customers);
        List<Product> products=inventoryRestClientService.allProducts().getContent().stream().toList();
        Long customerId=1L;
        Random random=new Random();
        Customer customer=customerRestClientService.customerById(customerId);
        for (int i = 0; i <20 ; i++) {
            Order order=Order.builder()
                    .customerId(customers.get(random.nextInt(customers.size())).getId())
                    .status(Math.random()>0.5? OrderStatus.PENDING: OrderStatus.CREATED)
                    .createdAt(new Date())
                    .build();
            Order savedOrder = orderRepository.save(order);
            for (int j = 0; j < products.size() ; j++) {
                if(Math.random()>0.70){
                    ProductItem productItem= ProductItem.builder()
                            .order(savedOrder)
                            .productId(products.get(j).getId())
                            .price(products.get(j).getPrice())
                            .quantity(1+random.nextInt(10))
                            .discount(Math.random())
                            .build();
                    productItemRepository.save(productItem);
                }

            }
        }
        return orderRepository.findAll();
    }




}
