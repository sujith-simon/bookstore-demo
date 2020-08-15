package com.demo.bookstore.exception;

import org.springframework.http.HttpStatus;

import com.demo.bookstore.constants.MessageConstants;
import com.demo.bookstore.exception.base.BaseClientException;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
public class OrderNotFoundException extends BaseClientException {


    public OrderNotFoundException(Integer id, Exception e) {
        super(HttpStatus.NOT_FOUND, String.format(MessageConstants.ORDER_NOT_FOUND, id), e);
    }

    public OrderNotFoundException(Integer id) {
        this(id, null);
    }
}
