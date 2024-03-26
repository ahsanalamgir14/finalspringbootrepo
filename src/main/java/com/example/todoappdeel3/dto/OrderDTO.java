package com.example.todoappdeel3.dto;

import com.example.todoappdeel3.Enums.OrderStatus;
import com.example.todoappdeel3.models.CustomUser;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class OrderDTO {

    public Long id;

    public String orderdescritpion;

    public Double amount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

    public LocalDate orderdate;


    public OrderStatus orderStatus;

    public String payment;
    

 @JsonAlias("user_id")
    public CustomUser user;


    @JsonAlias("cart_id")
    public List<CartComponentsDTO> cart;



//    public OrderDTO(String orderdescritpion, Double amount, Date orderdate, OrderStatus orderStatus, String payment, CustomUser user, List<CartitemsDTO> cart) {
//        this.orderdescritpion = orderdescritpion;
//        this.amount = amount;
//        this.orderdate = orderdate;
//        this.orderStatus = orderStatus;
//        this.payment = payment;
//        this.user = user;
//        this.cart = cart;
//    }

    public void setOrderdescritpion(String orderdescritpion) {
        this.orderdescritpion = orderdescritpion;
    }

    public void setamount(Double amount) {
        this.amount = amount;
    }

    public void setOrderdate(LocalDate orderdate) {
        this.orderdate = orderdate;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public void setCart(List<CartComponentsDTO> cart) {
        this.cart = cart;
    }

    public void setId(Long id) {
        this.id = id;
    }
}