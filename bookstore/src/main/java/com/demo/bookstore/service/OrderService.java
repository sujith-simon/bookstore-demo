package com.demo.bookstore.service;

import com.demo.bookstore.models.entity.Order;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
public interface OrderService {
    Integer placeOrder(Order order);

    Order getOrder(Integer id);
}
