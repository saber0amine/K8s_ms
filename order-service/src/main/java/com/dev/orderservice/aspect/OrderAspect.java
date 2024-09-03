package com.dev.orderservice.aspect;

import com.dev.orderservice.web.OrderRestController;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OrderAspect {

    @Autowired
    private OrderRestController orderRestController;

    private ThreadLocal<Boolean> isOrdersMethodCalled = ThreadLocal.withInitial(() -> false);

    @Before("execution(* com.dev.orderservice.web.OrderRestController.*(..)) || execution(* org.springframework.data.repository.Repository+.*(..))")
    public void beforeAnyMethod() {
        if (!isOrdersMethodCalled.get()) {
            try {
                isOrdersMethodCalled.set(true);
                orderRestController.orders();
            } finally {
                isOrdersMethodCalled.remove();
            }
        }
    }
}