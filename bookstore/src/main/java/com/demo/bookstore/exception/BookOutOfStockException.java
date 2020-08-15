package com.demo.bookstore.exception;

import org.springframework.http.HttpStatus;

import com.demo.bookstore.constants.MessageConstants;
import com.demo.bookstore.exception.base.BaseClientException;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
public class BookOutOfStockException extends BaseClientException {

    public BookOutOfStockException(String isbn, Exception e) {
        super(HttpStatus.BAD_REQUEST, String.format(MessageConstants.BOOK_OUT_OF_STOCK, isbn), e);
    }

    public BookOutOfStockException(String isbn) {
        this(isbn, null);
    }
}
