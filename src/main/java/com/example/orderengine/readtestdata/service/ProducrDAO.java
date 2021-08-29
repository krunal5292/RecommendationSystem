package com.example.orderengine.readtestdata.service;

import com.example.orderengine.modal.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducrDAO extends MongoRepository<Product, String> {

}
