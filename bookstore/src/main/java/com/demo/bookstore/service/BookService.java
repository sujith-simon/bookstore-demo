package com.demo.bookstore.service;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.demo.bookstore.models.entity.Book;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
public interface BookService {

    Page<Book> getBooks(Pageable pageable);

    void confirmExistence(String isbn);

    Book getBookDetails(String isbn);

    String createBook(Book book);

    Page<Book> searchBooks(Pageable pageable, String isbn, String title, String author);

    void update(Book book);

    Book addStock(String isbn, float stock);

    <T> T executeTransactionalInLockMode(String isbn, Function<Book, T> persistenceContextUpdater);
}
