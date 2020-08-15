package com.demo.bookstore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import com.demo.bookstore.helper.BaseApiTestClass;
import com.demo.bookstore.models.entity.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sujith Simon
 * Created on : 17/08/20
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SearchControllerApiTests extends BaseApiTestClass {

    private static final String existingBookId = "9781593275846";
    private static final String titleSubText = "Ja";
    private static final String authorSubText = "a";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void searchBooksTest() throws Exception {
        String responseJson = mockMvc.perform(get("/search/books").queryParam("isbn", existingBookId)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<Book> idResult = getBookList(responseJson);

        Assert.notEmpty(idResult, "Could not search book by id");

        responseJson = mockMvc.perform(get("/search/books").queryParam("title", titleSubText)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<Book> titleResult = getBookList(responseJson);

        Assert.notEmpty(titleResult, "Could not search book by title");


        responseJson = mockMvc.perform(get("/search/books").queryParam("author", authorSubText)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<Book> authorResult = getBookList(responseJson);

        Assert.notEmpty(authorResult, "Could not search book by title");


        responseJson = mockMvc.perform(get("/search/books").queryParam("isbn", existingBookId).queryParam("title", titleSubText)
                .queryParam("author", authorSubText)).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<Book> combinedResult = getBookList(responseJson);

        Assert.notEmpty(combinedResult, "Could not search book by search combination");

        Set<Book> intersectionResult = new HashSet<>();
        idResult.stream().filter(this::performSearchIntersection).forEach(intersectionResult::add);
        titleResult.stream().filter(this::performSearchIntersection).forEach(intersectionResult::add);
        authorResult.stream().filter(this::performSearchIntersection).forEach(intersectionResult::add);


        Assert.isTrue(combinedResult.size() == intersectionResult.size() && combinedResult.containsAll(intersectionResult),
                "Combined search is not working");
    }

    private List<Book> getBookList(String responseJson) throws JsonProcessingException {
        final Map<Object, Object> pageMap = fromJson(responseJson, new TypeReference<Map<Object, Object>>() {
        });
        return fromJson(toJson(pageMap.get("content")), new TypeReference<List<Book>>() {
        });
    }

    private boolean performSearchIntersection(Book book) {
        return book.getIsbn().equals(existingBookId) && book.getTitle().contains(titleSubText) && book.getAuthor().contains(authorSubText);
    }

}
