package com.demo.bookstore.models.external;

import com.demo.bookstore.models.MediaPost;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonPlaceholderMediaPost extends MediaPost {

    @JsonProperty("body")
    @Override
    public String getPost() {
        return super.getPost();
    }

    @JsonProperty("title")
    @Override
    public String getTitle() {
        return super.getTitle();
    }
}
