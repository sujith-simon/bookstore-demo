package com.demo.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bookstore.models.entity.Book;
import com.demo.bookstore.models.entity.Order;
import com.demo.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Purchase an existing book", responses = {
            @ApiResponse(description = "Order successfully created", responseCode = "201"),
    })
    @PostMapping
    public HttpEntity<Integer> placeOrder(@RequestBody Order order) {
        Integer orderId = orderService.placeOrder(order);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }


    @Operation(summary = "Get Order details")
    @GetMapping("/{id}")
    public HttpEntity<Order> getBookDetails(@PathVariable(value = "id") Integer id) {
        Order order = orderService.getOrder(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


}
