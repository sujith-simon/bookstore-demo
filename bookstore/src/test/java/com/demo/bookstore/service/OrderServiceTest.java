package com.demo.bookstore.service;

import java.util.Optional;
import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.demo.bookstore.exception.BookOutOfStockException;
import com.demo.bookstore.exception.OrderNotFoundException;
import com.demo.bookstore.models.entity.Book;
import com.demo.bookstore.models.entity.Order;
import com.demo.bookstore.repository.OrderRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Sujith Simon
 * Created on : 18/08/20
 */

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private BookService bookService;

    @Mock
    private OrderRepository orderRepository;

    @Test(expected = BookOutOfStockException.class)
    public void bookOutOfStockTest() {
        when(bookService.executeTransactionalInLockMode(anyString(), any())).then(o -> {
            Function<Book, Order> callback = o.getArgument(1);
            Book book = new Book();
            book.setStock(0f);
            return callback.apply(book);
        });

        Order order = new Order();
        order.setBookIsbn("test");
        order.setQuantity(5);
        orderService.placeOrder(order);
    }

    @Test(expected = OrderNotFoundException.class)
    public void bookNotFoundTest() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());
        orderService.getOrder(anyInt());
    }
}
