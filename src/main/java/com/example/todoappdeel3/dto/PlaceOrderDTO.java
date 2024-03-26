package com.example.todoappdeel3.dto;

import com.example.todoappdeel3.Enums.OrderStatus;
import com.example.todoappdeel3.models.CustomUser;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class PlaceOrderDTO {



    public String review;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate orderdate;


    public String payment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

    public LocalDate receiveDate;





}
