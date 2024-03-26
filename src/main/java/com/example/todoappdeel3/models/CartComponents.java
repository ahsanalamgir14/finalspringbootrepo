package com.example.todoappdeel3.models;

import com.example.todoappdeel3.dto.CartComponentsDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class CartComponents {


    @Id
    @GeneratedValue


    private Long id;

    private Double price;

    private Integer quantity;




    @ManyToOne(cascade =  CascadeType.MERGE)
    //@JoinColumn(name = "product_id")
    //@JsonIgnore
    @JsonBackReference
    private Product product;


    @ManyToOne(cascade = CascadeType.MERGE)
    //@JoinColumn(name = "user_id")
    //@JsonIgnore
    @JsonBackReference

    private CustomUser user;


    @ManyToOne(cascade = CascadeType.MERGE)
    //@JoinColumn(name = "orders_id")
    @JsonBackReference

    private Order order;

    private String imageUrl;

    private String productName;


    public CartComponents() {

    }
    public CartComponents(Double price, Integer quantity, Product product, CustomUser user, Order order, String imageUrl, String productName) {
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.user = user;
        this.order = order;
        this.imageUrl = imageUrl;
        this.productName = productName;
    }


    public CartComponentsDTO getcartItemDTO(){
        CartComponentsDTO CartComponentsDTO = new CartComponentsDTO();
        CartComponentsDTO.setId(id);
        CartComponentsDTO.setPrice(price);
        CartComponentsDTO.setProductid(product.getId());
        CartComponentsDTO.setOrderId(order.getId());
        CartComponentsDTO.setQuantity(quantity);
        CartComponentsDTO.setUserid(user.getId());
        return CartComponentsDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}


