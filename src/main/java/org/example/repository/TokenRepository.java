package org.example.repository;

import org.example.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Modifying
    @Query("update Token t set t.createdAt= :localDateTime where t.token = :token")
    void updateConfirmedAt(String token, LocalDateTime localDateTime);
}
