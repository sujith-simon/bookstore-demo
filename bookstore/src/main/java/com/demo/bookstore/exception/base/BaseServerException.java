package com.demo.bookstore.exception.base;

import org.springframework.http.HttpStatus;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
public abstract class BaseServerException extends BaseException {

    public BaseServerException(String errorMsg, Exception e) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg, e);
    }

    public BaseServerException(HttpStatus responseStatus, String errorMsg, Exception e) {
        super(responseStatus, errorMsg, e);
    }
}