package com.demo.bookstore.client;

import java.util.List;

import com.demo.bookstore.models.MediaPost;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
public interface MediaCoverageClient {
    List<MediaPost> getPosts(String isbn);
}
