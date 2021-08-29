package com.example.orderengine.modal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DBCollection {
    @Id
    private String id;
}
