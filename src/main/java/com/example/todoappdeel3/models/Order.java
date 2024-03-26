package com.example.todoappdeel3.models;

import com.example.todoappdeel3.Enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(columnDefinition="TEXT")
    private String review;

    private Double price;




    @Enumerated(EnumType.STRING)

    private OrderStatus orderStatus;

    @Column(columnDefinition="TEXT")
    private String payment;

    //@ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne(cascade = CascadeType.MERGE)
    //@JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private CustomUser user;


    @OneToMany(mappedBy = "order")
    private List<CartComponents> CartComponents;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate receiveDate;



    public Order(String discription, Double price, OrderStatus orderStatus, String payment, CustomUser user, List<CartComponents> CartComponents, LocalDate recieveDate) {
        this.review = discription;
        this.price = price;
        this.orderStatus = orderStatus;
        this.payment = payment;
        this.user = user;
        this.CartComponents = CartComponents;
        this.receiveDate = recieveDate;
    }

    public Order() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public List<CartComponents> getCartComponents() {
        return CartComponents;
    }

    public void setCartComponents(List<CartComponents> CartComponents) {
        this.CartComponents = CartComponents;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }
}