package com.demo.bookstore.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookstore.manager.MediaCoverageManager;
import com.demo.bookstore.models.MediaPost;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@Service
public class MediaCoverageServiceImpl implements MediaCoverageService {

    private static final Logger logger = LoggerFactory.getLogger(MediaCoverageServiceImpl.class);

    @Autowired
    private MediaCoverageManager mediaCoverageManager;

    @Autowired
    private BookService bookService;

    @Override
    public List<MediaPost> getPosts(String isbn) {
        bookService.confirmExistence(isbn);
        return mediaCoverageManager.getPosts(isbn);
    }
}
