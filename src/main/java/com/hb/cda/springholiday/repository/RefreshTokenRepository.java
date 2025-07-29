package com.hb.cda.springholiday.repository;

import com.hb.cda.springholiday.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.expiresAt < current_date")
    void deleteExpired();
}
