package com.demo.bookstore.exception;

import org.springframework.http.HttpStatus;

import com.demo.bookstore.constants.MessageConstants;
import com.demo.bookstore.exception.base.BaseClientException;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
public class BookAlreadyExistsException extends BaseClientException {

    public BookAlreadyExistsException(String isbn, Exception e) {
        super(HttpStatus.CONFLICT, String.format(MessageConstants.BOOK_ALREADY_EXISTS, isbn), e);
    }

    public BookAlreadyExistsException(String isbn) {
        this(isbn, null);
    }
}
