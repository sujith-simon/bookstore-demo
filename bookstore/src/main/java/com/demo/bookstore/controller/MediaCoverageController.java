package com.demo.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bookstore.models.MediaPost;
import com.demo.bookstore.service.MediaCoverageService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@RestController
@RequestMapping("/media-coverage")
public class MediaCoverageController {

    @Autowired
    private MediaCoverageService mediaCoverageService;

    @Operation(summary = "Get Media coverage of the book")
    @GetMapping
    public HttpEntity<List<MediaPost>> getPosts(@RequestParam(value = "isbn", required = true) String isbn) {
        final List<MediaPost> posts = mediaCoverageService.getPosts(isbn);
        return ResponseEntity.ok(posts);
    }
}
