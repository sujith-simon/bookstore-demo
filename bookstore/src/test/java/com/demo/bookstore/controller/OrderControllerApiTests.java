package com.demo.bookstore.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import com.demo.bookstore.helper.BaseApiTestClass;
import com.demo.bookstore.models.entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sujith Simon
 * Created on : 17/08/20
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class OrderControllerApiTests extends BaseApiTestClass {

    private static final String existingBookId = "9781593275846";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createOrderAndOrderDetailsTest() throws Exception {
        Order order = new Order();
        order.setBookIsbn(existingBookId);
        order.setQuantity(1);

        String orderId = mockMvc.perform(post("/order").content(toJson(order)).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value())).andReturn().getResponse().getContentAsString();


        String orderJson = mockMvc.perform(get("/order/{0}", orderId)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Order savedOrder = fromJson(orderJson, Order.class);

        Assert.isTrue(order.getBookIsbn().equals(savedOrder.getBookIsbn()), "Order isbn was not saved correctly");
        Assert.isTrue(order.getQuantity().equals(savedOrder.getQuantity()), "Order quantity was not saved correctly");

    }

}
