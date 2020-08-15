package com.demo.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bookstore.models.entity.Book;
import com.demo.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private BookService booksService;


    @Operation(summary = "Search books based on isbn, title and author")
    @GetMapping("/books")
    public HttpEntity<Page<Book>> searchBooks(Pageable pageable,
                                              @RequestParam(value = "isbn", required = false) String isbn,
                                              @RequestParam(value = "title", required = false) String title,
                                              @RequestParam(value = "author", required = false) String author) {
        Page<Book> books = booksService.searchBooks(pageable, isbn, title, author);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

}
