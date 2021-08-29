package com.example.orderengine.modal;

import com.example.orderengine.modal.Product;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Order {
    @Id
    private String id;
    private List<Product> products;
    private LocalDateTime localDateTime = LocalDateTime.now();
}
