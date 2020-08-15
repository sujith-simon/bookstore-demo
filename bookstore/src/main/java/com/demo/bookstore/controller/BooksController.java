package com.demo.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bookstore.models.entity.Book;
import com.demo.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * @author Sujith Simon
 * Created on : 14/08/20
 */
@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BookService booksService;


    @Operation(summary = "Get the list of books")
    @GetMapping
    public HttpEntity<Page<Book>> getBooks(Pageable pageable) {
        Page<Book> books = booksService.getBooks(pageable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Get book details")
    @GetMapping("/{isbn}")
    public HttpEntity<Book> getBookDetails(@PathVariable(value = "isbn") String isbn) {
        Book book = booksService.getBookDetails(isbn);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    @Operation(summary = "Create a new book", responses = {
            @ApiResponse(description = "Book successfully created", responseCode = "201"),
    })
    @PostMapping
    public HttpEntity<String> createBook(@RequestBody Book book) {
        String isbn = booksService.createBook(book);
        return new ResponseEntity<>(isbn, HttpStatus.CREATED);
    }

    @Operation(summary = "Add Stock to existing book", responses = {
            @ApiResponse(description = "Updated Book stock", responseCode = "200"),
    })
    @PostMapping("/add-stock/{isbn}")
    public HttpEntity<Book> addStock(@PathVariable(value = "isbn") String isbn, @RequestParam(value = "stock", required = true) float stock) {
        Book book = booksService.addStock(isbn, stock);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

}
