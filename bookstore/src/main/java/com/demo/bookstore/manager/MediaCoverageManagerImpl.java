package com.demo.bookstore.manager;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookstore.client.MediaCoverageClient;
import com.demo.bookstore.models.MediaPost;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@Service
public class MediaCoverageManagerImpl implements MediaCoverageManager {

    private static final Logger logger = LoggerFactory.getLogger(MediaCoverageManagerImpl.class);

    private volatile Cache<String, List<MediaPost>> postsCacheMap;

    @Autowired
    private MediaCoverageClient mediaCoverageClient;

    @PostConstruct
    private void init() {
        postsCacheMap = CacheBuilder.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(100).build();
    }

    @HystrixCommand(fallbackMethod = "getPostsFallback")
    @Override
    public List<MediaPost> getPosts(String isbn) {
        List<MediaPost> posts = mediaCoverageClient.getPosts(isbn);
        if (!posts.isEmpty()) {
            postsCacheMap.put(isbn, posts);
        }
        return posts;
    }


    public List<MediaPost> getPostsFallback(String sbin) {
        logger.warn("Executing external client connection failure fallback");

        List<MediaPost> cachedPosts = postsCacheMap.getIfPresent(sbin);
        if (cachedPosts != null) {
            logger.debug("Serving data for '{}' from cache.", sbin);
            return cachedPosts;
        }
        logger.debug("No cache found for '{}' sending empty data.", sbin);
        return Collections.emptyList();
    }
}
