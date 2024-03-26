package com.example.todoappdeel3.repository;

import com.example.todoappdeel3.Enums.UserRole;
import com.example.todoappdeel3.models.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByEmail(String email);

    CustomUser findByUserRole(UserRole userRole);

    Optional<CustomUser> findCustomUserById(Long userid);


}
