package com.demo.bookstore.helper;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Sujith Simon
 * Created on : 17/08/20
 */
public class BaseApiTestClass {

    @Autowired
    private ObjectMapper objectMapper;


    protected String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    protected <T> T fromJson(String string, Class<T> type) throws JsonProcessingException {
        return objectMapper.readValue(string, type);
    }


    protected <T> T fromJson(String string, TypeReference<T> type) throws JsonProcessingException {
        return objectMapper.readValue(string, type);
    }
}
