package com.demo.bookstore.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.demo.bookstore.exception.BookAlreadyExistsException;
import com.demo.bookstore.exception.BookNotFoundException;
import com.demo.bookstore.models.entity.Book;
import com.demo.bookstore.repository.BooksRepository;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Sujith Simon
 * Created on : 18/08/20
 */

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BooksServiceImpl booksService;

    @Mock
    private BooksRepository booksRepository;


    @Test(expected = BookNotFoundException.class)
    public void bookNotFoundTest() {
        when(booksRepository.findById(anyString())).thenReturn(Optional.empty());
        booksService.getBookDetails(anyString());
    }

    @Test
    public void bookDetailsTest() {
        Book book = new Book();
        when(booksRepository.findById(anyString())).thenReturn(Optional.of(book));
        Book gotBook = booksService.getBookDetails(anyString());
        Assert.assertSame(gotBook, book);
    }

    @Test
    public void createBookTest() {
        when(booksRepository.existsById(anyString())).thenReturn(false);
        when(booksRepository.save(any(Book.class))).then(returnsFirstArg());

        Book book = new Book();
        book.setIsbn("test");
        final String bookId = booksService.createBook(book);
        Assert.assertEquals(bookId, book.getIsbn());
    }


    @Test(expected = BookAlreadyExistsException.class)
    public void createDuplicateBookTest() {
        when(booksRepository.existsById(anyString())).thenReturn(true);
        Book book = new Book();
        book.setIsbn("test");
        booksService.createBook(book);
    }

    @Test(expected = BookNotFoundException.class)
    public void confirmExistenceTest() {
        when(booksRepository.existsById(anyString())).thenReturn(false);
        booksService.confirmExistence(anyString());
    }
}
