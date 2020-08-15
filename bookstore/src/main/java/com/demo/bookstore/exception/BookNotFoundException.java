package com.demo.bookstore.exception;

import org.springframework.http.HttpStatus;

import com.demo.bookstore.constants.MessageConstants;
import com.demo.bookstore.exception.base.BaseClientException;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
public class BookNotFoundException extends BaseClientException {


    public BookNotFoundException(String isbn, Exception e) {
        super(HttpStatus.NOT_FOUND, String.format(MessageConstants.BOOK_NOT_FOUND, isbn), e);
    }

    public BookNotFoundException(String isbn) {
        this(isbn, null);
    }
}
