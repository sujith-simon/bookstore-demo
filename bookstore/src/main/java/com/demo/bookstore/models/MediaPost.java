package com.demo.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sujith Simon
 * Created on : 14/08/20
 */
@Getter
@Setter
public class MediaPost {
    private String title;
    private String post;
}
