package com.example.orderengine.order;

import com.example.orderengine.modal.Order;
import com.example.orderengine.modal.Product;
import com.example.orderengine.readtestdata.service.ProductService;
import com.example.orderengine.recommendationengine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductService productService;

    @Autowired
    private Engine engine;

    public List<Order> findAll() {
        try {
            return this.orderDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void createTestData() {
        try {
            List<Product> productList = this.productService.findAll();
            int orders = 0;
            while (orders < 20) {
                Order order = new Order();
                int[] randomNumbes = getRandomNumbes(productList.size());
                List<Product> products = new ArrayList<>();
                for (int number : randomNumbes) {
                    products.add(productList.get(number));
                }
                order.setProducts(products);
                this.placeOrder(order);
                orders++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Order placeOrder(Order order) {
        try {
            order = this.orderDAO.save(order);
            return order;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] getRandomNumbes(int size) {
        int max = getRandomInt(0, 50);
        int min = 0;
        int[] ints = new int[max];
        int count = 0;
        while (count < max) {
            ints[count++] = getRandomInt(0, size);
        }
        return ints;
    }

    public static int getRandomInt(int min, int max) {
        int random_int = (int) (Math.random() * (max - min + 1) + min);
        return random_int;
    }
}
