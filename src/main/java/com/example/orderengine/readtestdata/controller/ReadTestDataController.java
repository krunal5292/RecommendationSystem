package com.example.orderengine.readtestdata.controller;

import com.example.orderengine.order.OrderService;
import com.example.orderengine.modal.Order;
import com.example.orderengine.readtestdata.service.ProductService;
import com.example.orderengine.recommendationengine.Engine;
import com.github.chen0040.fpm.data.ItemSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ReadTestDataController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private Engine engine;

    @GetMapping("/readTestData")
    public ResponseEntity<Boolean> startReading() {
        return ResponseEntity.ok(productService.readTestData());
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.placeOrder(order));
    }

    @GetMapping("/getRecommendedProducts/{productName}")
    public ResponseEntity<List<String>> getRecommendedProducts(@PathVariable String productName) {
        return ResponseEntity.ok(engine.getRecommendedProducts(productName));
    }

}
