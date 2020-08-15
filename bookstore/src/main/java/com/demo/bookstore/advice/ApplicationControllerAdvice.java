package com.demo.bookstore.advice;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.bookstore.constants.MessageConstants;
import com.demo.bookstore.exception.base.BaseException;
import com.demo.bookstore.models.error.ErrorObject;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@ControllerAdvice
public class ApplicationControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationControllerAdvice.class);

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorObject> handleGlobalException(Throwable e) {
        return getErrorObjectResponseEntity(MessageConstants.INTERNAL_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<ErrorObject> handleBaseException(BaseException e) {
        return getErrorObjectResponseEntity(e.getErrorMsg(), e.getResponseStatus(), e);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorObject> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        final Throwable cause = e.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            String message = String.format("Cannot convert %s to type %s", ife.getValue(), ife.getTargetType());
            return getErrorObjectResponseEntity(message, HttpStatus.BAD_REQUEST, cause);
        }
        return handleGlobalException(e);
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<ErrorObject> handleConstraintViolationException(TransactionSystemException e) {
        Throwable cause = e.getRootCause();
        if (cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
            String message = constraintViolations.stream().map(v -> v.getPropertyPath() + "->" + v.getMessage()).collect(Collectors.joining(","));
            return getErrorObjectResponseEntity(message, HttpStatus.BAD_REQUEST, cause);
        }
        return handleGlobalException(e);
    }

    private ResponseEntity<ErrorObject> getErrorObjectResponseEntity(String errorMsg, HttpStatus responseStatus, Throwable e) {
        logger.error("Encountered error", e);
        ErrorObject errorObject = new ErrorObject(errorMsg, e.getMessage());
        return new ResponseEntity<>(errorObject, responseStatus);
    }
}