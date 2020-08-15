package com.demo.bookstore.exception.base;

import org.springframework.http.HttpStatus;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
public abstract class BaseException extends RuntimeException {

    private final HttpStatus responseStatus;
    private final String errorMsg;

    public BaseException(HttpStatus responseStatus, String errorMsg, Exception e) {
        super(e);
        this.responseStatus = responseStatus;
        this.errorMsg = errorMsg;
    }


    public HttpStatus getResponseStatus() {
        return responseStatus;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
