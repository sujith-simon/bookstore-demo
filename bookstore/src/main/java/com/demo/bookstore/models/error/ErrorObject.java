package com.demo.bookstore.models.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorObject {
    private String message;
    private String exceptionMessage;
}
