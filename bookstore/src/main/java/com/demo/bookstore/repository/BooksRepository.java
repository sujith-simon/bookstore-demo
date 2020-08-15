package com.demo.bookstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.demo.bookstore.models.entity.Book;

/**
 * @author Sujith Simon
 * Created on : 14/08/20
 */
public interface BooksRepository extends PagingAndSortingRepository<Book, String> {

    String SEARCH_QUERY = "select new Book(b.isbn,b.title,b.author,b.price) from Book b where " +
            "(:isbn is null or b.isbn=:isbn) and " +
            "(:title is null or b.title like %:title%) and " +
            "(:author is null or b.author like %:author%)";

    @Query(value = SEARCH_QUERY)
    Page<Book> searchBooks(Pageable pageable, @Param("isbn") String isbn, @Param("title") String title, @Param("author") String author);
}
