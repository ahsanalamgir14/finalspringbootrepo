package com.example.todoappdeel3.controller;


import com.example.todoappdeel3.dto.CartComponentsDTO;
import com.example.todoappdeel3.dto.PlaceOrderDTO;
import com.example.todoappdeel3.models.Order;
import com.example.todoappdeel3.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cart")
@CrossOrigin(origins ={"http://localhost:4200", "http://s1149822.student.inf-hsleiden.nl:19822"})
public class CartComponentsController {

    private final UserService userService;


    @Autowired
    public CartComponentsController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<?> addProductToCart(@RequestBody CartComponentsDTO cartcomponentsDTO){
        return userService.addProductToCart(cartcomponentsDTO);
    }


    @GetMapping("/{userid}")
    public ResponseEntity<Order> getCartbyuserid(@PathVariable Long userid) {

        return  userService.getCartByUserId(userid);

    }

    @GetMapping("/placed/{userid}")
    public ResponseEntity<?> getAllplacedOrdersbyuserid(@PathVariable Long userid) {

        return  userService.getAllPlacedOrdersByUserId(userid);

    }

    @GetMapping("/placed/{userid}/{orderId}")
    public ResponseEntity<Order> getplacedOrderbyuserid(@PathVariable Long userid, @PathVariable Long orderId) {

        return  userService.getPlacedOrderByUserId(userid, orderId);

    }

    @DeleteMapping("/delete")

    public ResponseEntity<String> deleteById(@RequestBody CartComponentsDTO cartcomponentsDTO){

        return this.userService.deleteOrderCartComponentsBycartimtemId(cartcomponentsDTO);
    }

    @DeleteMapping("/checkoutCart")

    public ResponseEntity<String> checkoutCart(@RequestBody CartComponentsDTO cartcomponentsDTO){

        return this.userService.checkoutCart(cartcomponentsDTO);
    }

    @PostMapping("{userid}/minus/{productId}")
    public ResponseEntity<String> minusCartItemQuantity(@PathVariable Long userid, @PathVariable Long productId){
        return this.userService.minusQuantity(userid, productId);
    }


    @PostMapping("{userid}/add/{productId}")
    public ResponseEntity<String> addCartItemQuantity(@PathVariable Long userid, @PathVariable Long productId){
        return this.userService.addQuantity(userid, productId);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Long orderId, @RequestBody PlaceOrderDTO placeOrderDTO){
        return this.userService.findOrderById(orderId, placeOrderDTO);
    }


}

