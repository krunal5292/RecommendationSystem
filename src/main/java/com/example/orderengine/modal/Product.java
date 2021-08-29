package com.example.orderengine.modal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@EqualsAndHashCode
public class Product {
    @Id
    private String id;
    private String productId;
    private String customerIP;
    private String brandName;
    private String productDetails;
}
