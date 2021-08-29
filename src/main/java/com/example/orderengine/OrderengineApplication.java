package com.example.orderengine;

import com.example.orderengine.order.OrderService;
import com.example.orderengine.readtestdata.service.ProductService;
import com.example.orderengine.recommendationengine.Engine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OrderengineApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderengineApplication.class, args);
        OrderService orderService = applicationContext.getBean(OrderService.class);
        ProductService productService = applicationContext.getBean(ProductService.class);
        Engine engine = applicationContext.getBean(Engine.class);
//        productService.findAll().forEach(System.out::println);
//        orderService.createTestData();
    }

}
