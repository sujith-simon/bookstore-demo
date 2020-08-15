package com.demo.bookstore.service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.bookstore.exception.BookAlreadyExistsException;
import com.demo.bookstore.exception.BookNotFoundException;
import com.demo.bookstore.models.entity.Book;
import com.demo.bookstore.repository.BooksRepository;


/**
 * @author Sujith Simon
 * Created on : 14/08/20
 */
@Service
public class BooksServiceImpl implements BookService {

    public static final Map<String, Object> LOCK_PROPERTIES = Collections.singletonMap(
            "javax.persistence.lock.timeout", TimeUnit.SECONDS.toMillis(1)
    );

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MediaCoverageService mediaCoverageService;


    @Transactional(readOnly = true)
    @Override
    public Page<Book> getBooks(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Book> searchBooks(Pageable pageable, String isbn, String title, String author) {

        return booksRepository.searchBooks(pageable, isbn, title, author);
    }

    @Transactional
    @Override
    public void update(Book book) {
        confirmExistence(book.getIsbn());
        booksRepository.save(book);
    }

    @Transactional
    @Override
    public Book addStock(String isbn, float stock) {
        return executeTransactionalInLockMode(isbn, persistenceContextBook -> {
            float targetStock = persistenceContextBook.getStock() + stock;
            persistenceContextBook.setStock(targetStock);
            return persistenceContextBook;
        });
    }

    @Transactional
    @Override
    public <T> T executeTransactionalInLockMode(String isbn, Function<Book, T> persistenceContextUpdater) {
        confirmExistence(isbn);
        Book book = entityManager.find(Book.class, isbn, LockModeType.PESSIMISTIC_WRITE, LOCK_PROPERTIES);
        T returnValue = persistenceContextUpdater.apply(book);
        booksRepository.save(book);
        return returnValue;
    }

    @Transactional(readOnly = true)
    @Override
    public void confirmExistence(String isbn) {
        if (!booksRepository.existsById(isbn)) {
            throw new BookNotFoundException(isbn);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBookDetails(String isbn) {
        final Optional<Book> book = booksRepository.findById(isbn);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException(isbn);
        }
    }

    @Transactional
    @Override
    public String createBook(Book book) {
        if (booksRepository.existsById(book.getIsbn())) {
            throw new BookAlreadyExistsException(book.getIsbn());
        }

        final Book newBook = booksRepository.save(book);
        return newBook.getIsbn();
    }


}
