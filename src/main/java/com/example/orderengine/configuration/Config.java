package com.example.orderengine.configuration;

import com.example.orderengine.modal.DBCollection;
import com.example.orderengine.modal.Order;
import com.example.orderengine.modal.Product;
import com.example.orderengine.order.OrderService;
import com.example.orderengine.readtestdata.service.ProductService;
import com.example.orderengine.recommendationengine.Engine;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Configuration
public class Config {

    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private Engine engine;

    @Bean
    public void createMongoDB() {
        MongoDatabase db = mongoClient.getDatabase("OrderEngine");
        MongoCollection<Document> collection = db.getCollection("test");
        System.out.println("----- CREATING DATABASE IF DOES NOT EXIST -----");
        if (collection.countDocuments() <= 0) {
            db.createCollection("test");
            collection = db.getCollection("test");
            Document document = new Document("_id", 1);
            collection.insertOne(document);
            System.out.println("----- DATABASE CREATED SUCCESS -----");
        }
    }

    @Bean
    @DependsOn("createMongoDB")
    public void readProductList() {
        if (productService != null) {
            System.out.println("----- PRODUCTS TRANSACTION READING -----");
            List<Product> productList = productService.findAll();
            if (productList != null && !productList.isEmpty()) {
                System.out.println("----- PRODUCTS TRANSACTION EXIST -----");
                return;
            }
            productService.readTestData();
            System.out.println("----- PRODUCTS TRANSACTION READING SUCCESS -----");
        } else {
            System.out.println("----- ERROR WHILE READING PRODUCTS -----");
        }
    }

    @Bean
    @DependsOn("readProductList")
    public void createTestData() {
        List<Order> orders = orderService.findAll();
        System.out.println("----- SAMPLE ORDER TRANSACTION CREATING -----");
        if (orders == null || orders.isEmpty()) {
            orderService.createTestData();
        }
        System.out.println("----- SAMPLE ORDER TRANSACTION CREATED SUCCESS -----");
    }

    @Bean
    @DependsOn("createTestData")
    public void prepareModal() {
        engine.updateModal();
    }

}
