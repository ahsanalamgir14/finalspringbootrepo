package com.example.todoappdeel3.repository;

import com.example.todoappdeel3.models.CartComponents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartComponentsRepository extends JpaRepository<CartComponents, Long> {

    //List<Cart> findAllByUserId(Long, userid)
    Optional<CartComponents> findByUserId(long id);

    Optional<CartComponents> findCartComponentsByOrderId(Long orderId);

    // Optional<CartComponents> findCartComponentsById(Long id);


    Optional<CartComponents> findByUserIdAndProductIdAndOrderId(Long userid, Long productid, Long orderId);
}