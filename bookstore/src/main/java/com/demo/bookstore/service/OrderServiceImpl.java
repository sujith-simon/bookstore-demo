package com.demo.bookstore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.bookstore.controller.BooksController;
import com.demo.bookstore.exception.BookOutOfStockException;
import com.demo.bookstore.exception.OrderNotFoundException;
import com.demo.bookstore.models.entity.Order;
import com.demo.bookstore.repository.OrderRepository;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderRepository orderRepository;


    /**
     * This method will throw {@link BookOutOfStockException} if there is no stock.
     * The stock can only be added by the shop owner
     *
     * @see BooksController#addStock(String, float)}
     *
     * Here {@link Transactional} is not added because it is managed by {@link BookService}
     */
    @Override
    public Integer placeOrder(Order order) {
        final String bookIsbn = order.getBookIsbn();

        Order savedOrder = bookService.executeTransactionalInLockMode(bookIsbn, persistenceContextBook -> {
                    float targetStock = persistenceContextBook.getStock() - order.getQuantity();
                    if (targetStock < 0) {
                        throw new BookOutOfStockException(order.getBookIsbn());
                    }

                    persistenceContextBook.setStock(targetStock);

                    order.setOrderId(null); //to strictly create
                    return orderRepository.save(order);
                }
        );

        return savedOrder.getOrderId();
    }

    @Transactional(readOnly = true)
    @Override
    public Order getOrder(Integer id) {
        final Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new OrderNotFoundException(id);
        }

        return orderOptional.get();
    }

}
