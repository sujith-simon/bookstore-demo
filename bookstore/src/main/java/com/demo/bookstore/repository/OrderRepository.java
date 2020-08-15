package com.demo.bookstore.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.demo.bookstore.models.entity.Order;

/**
 * @author Sujith Simon
 * Created on : 14/08/20
 */
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {

}
