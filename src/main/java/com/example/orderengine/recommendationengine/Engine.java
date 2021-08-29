package com.example.orderengine.recommendationengine;

import com.example.orderengine.modal.Order;
import com.example.orderengine.order.OrderService;
import com.github.chen0040.fpm.AssocRuleMiner;
import com.github.chen0040.fpm.apriori.Apriori;
import com.github.chen0040.fpm.data.ItemSet;
import com.github.chen0040.fpm.data.ItemSets;
import com.github.chen0040.fpm.data.MetaData;
import com.github.chen0040.fpm.fpg.FPGrowth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Engine {

    @Autowired
    private OrderService orderService;

    private static ItemSets fis = null;
    private static ItemSets max_frequent_item_sets = null;

    //    @PostConstruct
    public void updateModal() {
        Runnable task = () -> {
            try {
                System.out.println("----- START MODAL UPDATE -----");
                List<Order> orderServiceAll = this.orderService.findAll();
                List<List<String>> dataset = new ArrayList<>();
                orderServiceAll.forEach(e -> dataset.add(e.getProducts().stream().map(product -> product.getProductDetails()).collect(Collectors.toList())));
                AssocRuleMiner method = new FPGrowth();
                method.setMinSupportLevel(2);
                MetaData metaData = new MetaData(dataset);

                // obtain all frequent item sets with support level not below 2
                fis = method.minePatterns(dataset, metaData.getUniqueItems());
//                fis.stream().forEach(itemSet -> System.out.println("item-set: " + itemSet));

//                System.out.println("-------------------------------------------------------------------------");
                // obtain the max frequent item sets
                max_frequent_item_sets = method.findMaxPatterns(dataset, metaData.getUniqueItems());
//                max_frequent_item_sets.stream().forEach(itemSet -> System.out.println("item-set: " + itemSet));

                System.out.println("----- MODAL UPDATE DONE -----");
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
    }

    public List<String> getRecommendedProducts(String productName) {
        Map<ItemSet, Integer> frequencycheckMap = new HashMap<>();
        Map<ItemSet, Set<String>> uniqueItemSet = new HashMap<>();

        max_frequent_item_sets.stream().forEach(itemSet -> {
            Set<String> products = new HashSet<>(itemSet.getItems().stream().map(e -> e.toLowerCase()).collect(Collectors.toList()));
            uniqueItemSet.put(itemSet, products);
        });
        max_frequent_item_sets.stream().forEach(itemSet -> {
            Set<String> set = uniqueItemSet.get(itemSet);
            frequencycheckMap.put(itemSet, set.contains(productName.toLowerCase()) ? frequencycheckMap.get(productName.toLowerCase()) != null ? frequencycheckMap.get(productName.toLowerCase()) + 5 : 5 : 0);
        });

//        frequencycheckMap.forEach((k, v) -> System.out.println(k + " : " + v));
        Map<ItemSet, Integer> sortedByCount = frequencycheckMap.entrySet()
                .stream()
                .sorted((Map.Entry.<ItemSet, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedByCount.keySet().stream()
                .flatMap(aa -> aa.getItems().stream())
                .limit(5).collect(Collectors.toList());
    }
}
