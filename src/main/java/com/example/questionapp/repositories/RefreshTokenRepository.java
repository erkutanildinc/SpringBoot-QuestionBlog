package com.example.questionapp.repositories;

import com.example.questionapp.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    RefreshToken findByUserId(Long userId);
}
