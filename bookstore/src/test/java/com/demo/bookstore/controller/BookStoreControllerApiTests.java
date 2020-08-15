package com.demo.bookstore.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import com.demo.bookstore.helper.BaseApiTestClass;
import com.demo.bookstore.helper.RandomGenerator;
import com.demo.bookstore.models.entity.Book;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sujith Simon
 * Created on : 17/08/20
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BookStoreControllerApiTests extends BaseApiTestClass {

    private static final String existingBookId = "9781593275846";

    @Autowired
    private MockMvc mockMvc;


    @Test
    void listBooksTest() throws Exception {
        mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void bookDetailsTest() throws Exception {
        mockMvc.perform(get("/books/{0}", existingBookId)).andDo(print()).andExpect(status().isOk());
    }


    @Test
    void bookNotFoundTest() throws Exception {
        mockMvc.perform(get("/books/xyz")).andDo(print()).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }


    @Test
    void bookCreationTest() throws Exception {
        String isbn = RandomGenerator.getString();

        Book book = new Book();
        book.setIsbn(isbn);
        book.setAuthor(RandomGenerator.getString());
        book.setTitle(RandomGenerator.getString());
        book.setPrice(5f);

        mockMvc.perform(post("/books").content(toJson(book)).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()));

        final String responseJson = mockMvc.perform(get("/books/{0}", isbn)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        final Book bookResponse = fromJson(responseJson, Book.class);

        Assert.isTrue(book.equals(bookResponse), "There was a data mismatch after saving");
    }


    @Test
    void bookCreationValidationTest() throws Exception {
        //isbn
        Book book = new Book("", RandomGenerator.getString(), RandomGenerator.getString(), RandomGenerator.getFloat());
        mockMvc.perform(post("/books").content(toJson(book)).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        //title
        book = new Book(RandomGenerator.getString(), null, RandomGenerator.getString(), RandomGenerator.getFloat());
        mockMvc.perform(post("/books").content(toJson(book)).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        //author
        book = new Book(RandomGenerator.getString(), RandomGenerator.getString(), null, RandomGenerator.getFloat());
        mockMvc.perform(post("/books").content(toJson(book)).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        //price
        book = new Book(RandomGenerator.getString(), RandomGenerator.getString(), RandomGenerator.getString(), null);
        mockMvc.perform(post("/books").content(toJson(book)).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    }


    @Test
    void bookStockAdditionTest() throws Exception {

        String responseJson = mockMvc.perform(get("/books/{0}", existingBookId)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Book initialBook = fromJson(responseJson, Book.class);


        Float stockToAdd = 5f;
        mockMvc.perform(post("/books/add-stock/{0}", existingBookId).queryParam("stock", String.valueOf(stockToAdd))).andDo(print())
                .andExpect(status().isOk());

        responseJson = mockMvc.perform(get("/books/{0}", existingBookId)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Book finalBook = fromJson(responseJson, Book.class);


        Assert.isTrue(finalBook.getStock() == initialBook.getStock() + stockToAdd, "The stock was not updated in the database");
    }


}
