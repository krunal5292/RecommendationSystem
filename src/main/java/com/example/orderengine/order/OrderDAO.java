package com.example.orderengine.order;

import com.example.orderengine.modal.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO extends MongoRepository<Order, String> {
}
