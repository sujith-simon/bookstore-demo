package com.demo.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.bookstore.models.MediaPost;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@Service
public interface MediaCoverageService {
    List<MediaPost> getPosts(String isbn);
}
