package com.demo.bookstore.exception.base;

import org.springframework.http.HttpStatus;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
public abstract class BaseClientException extends BaseException {

    public BaseClientException(String errorMsg) {
        this(errorMsg, null);
    }

    public BaseClientException(String errorMsg, Exception e) {
        this(HttpStatus.BAD_REQUEST, errorMsg, e);
    }

    public BaseClientException(HttpStatus responseStatus, String errorMsg, Exception e) {
        super(responseStatus, errorMsg, e);
    }
}
