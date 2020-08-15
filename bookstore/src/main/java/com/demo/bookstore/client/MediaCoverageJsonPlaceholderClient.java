package com.demo.bookstore.client;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.demo.bookstore.models.MediaPost;
import com.demo.bookstore.models.external.JsonPlaceholderMediaPost;
import com.demo.bookstore.util.MediaPostUtil;

/**
 * @author Sujith Simon
 * Created on : 14/08/20
 */
@Service
public class MediaCoverageJsonPlaceholderClient implements MediaCoverageClient {
    private static final Logger logger = LoggerFactory.getLogger(MediaCoverageJsonPlaceholderClient.class);

    private RestTemplate restTemplate;

    @Value("${configuration.media-conerage-api}")
    private String mediaCoverageApi;


    @PostConstruct
    private void init() {
        restTemplate = new RestTemplate();
    }

    public List<MediaPost> getPosts(String sbin) {
        try {
            ResponseEntity<List<JsonPlaceholderMediaPost>> responseEntity = restTemplate.exchange(mediaCoverageApi, HttpMethod.GET, null, new ParameterizedTypeReference<List<JsonPlaceholderMediaPost>>() {
            });

            List<JsonPlaceholderMediaPost> body = responseEntity.getBody();
            if (body != null) {
                final List<MediaPost> mediaPosts = body.stream().map(jsobPost -> (MediaPost) jsobPost).collect(Collectors.toList());
                return MediaPostUtil.filterPosts(sbin, mediaPosts);
            }
            return Collections.emptyList();
        } catch (RestClientResponseException e) {
            logger.error("Got response code {} with body {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            throw e;
        }
    }

}
