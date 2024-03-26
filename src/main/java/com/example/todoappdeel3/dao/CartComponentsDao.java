package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.models.CartComponents;
import com.example.todoappdeel3.repository.CartComponentsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component

public class CartComponentsDao {

    private final CartComponentsRepository cartComponentsRepository;


    public CartComponentsDao(CartComponentsRepository cartComponentsRepository) {
        this.cartComponentsRepository = cartComponentsRepository;
    }

    public CartComponents getCartbyuserid(Long id){
        Optional<CartComponents> cart =this.cartComponentsRepository.findByUserId(id);

        if (cart.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No cartcomponent found with that id"
            );
        }

        return cart.get();

    }



    //public void AddItem(CartDTO cartDTO) {
    //this.cartComponentsRepository.save((cartDTO.));
    // }


}