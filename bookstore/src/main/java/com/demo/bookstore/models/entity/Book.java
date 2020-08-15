package com.demo.bookstore.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.demo.bookstore.repository.BooksRepository;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sujith Simon
 * Created on : 14/08/20
 */
@Getter
@Setter
@Entity
@Table(name = "book")
@EqualsAndHashCode
public class Book {

    @Id
    @Size(min = 2)
    private String isbn;

    @NotNull
    @Size(min = 2)
    private String title;

    @NotNull
    @Size(min = 2)
    private String author;

    @NotNull
    @Min(1)
    private Float price;

    private Float stock;

    private String subtitle;
    private String published;
    private String publisher;
    private String website;
    private Integer pages;
    @Column(columnDefinition = "TEXT")
    private String description;

    public Book() {
    }

    /**
     * This constructor is used by {@link BooksRepository}.
     *
     * @param isbn   the isbn
     * @param title  the title
     * @param author the author
     * @param price  the price
     *
     * @see BooksRepository#SEARCH_QUERY
     */
    public Book(String isbn, String title, String author, Float price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
    }


}
