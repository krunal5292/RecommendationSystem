package com.example.orderengine.utility;

import com.example.orderengine.modal.Order;
import com.example.orderengine.modal.Product;
import com.example.orderengine.readtestdata.service.ProductService;
import com.github.chen0040.fpm.AssocRuleMiner;
import com.github.chen0040.fpm.data.MetaData;
import com.github.chen0040.fpm.fpg.FPGrowth;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Data
public class ProductCatchManager {

    @Autowired
    private ProductService productService;

    private static Map<String, Product> produtCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void initCache() {
        Runnable task = () -> {
            try {
                List<Product> products = productService.findAll();
                if (products != null && !products.isEmpty()) {
                    products.forEach(product -> {
                        produtCache.put(product.getProductId(), product);
                    });
                    System.out.println("----- PRODUCTS CACHE UPDATED -----");
                }
                Thread.sleep(600000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();

    }

    public Product getProduct(String productId) {
        return produtCache.get(productId);
    }
}
