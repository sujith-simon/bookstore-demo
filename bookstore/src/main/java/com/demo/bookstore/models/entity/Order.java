package com.demo.bookstore.models.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "book_order")
public class Order {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer orderId;

    @NotNull
    private String bookIsbn;

    @NotNull
    @Min(1)
    private Integer quantity;

    public Order() {
    }
}
