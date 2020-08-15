package com.demo.bookstore.runner;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.demo.bookstore.models.entity.Book;
import com.demo.bookstore.repository.BooksRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Sujith Simon
 * Created on : 17/08/20
 */
@Component
@ConditionalOnProperty(name = "configuration.profile", havingValue = "demo", matchIfMissing = false)
public class LoadSampleData implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LoadSampleData.class);
    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        if (booksRepository.count() == 0) {
            try {
                ClassPathResource resource = new ClassPathResource("sampleData.json");
                final List<Book> books = objectMapper.readValue(new BufferedInputStream(resource.getInputStream()), new TypeReference<List<Book>>() {
                });
                booksRepository.saveAll(books);
            } catch (IOException e) {
                logger.error("Could not load initial data.", e);
            }
        }
    }
}
