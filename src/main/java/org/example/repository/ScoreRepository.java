package org.example.repository;

import org.example.entity.Order;
import org.example.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("select s from Score s where s.order.id = :id")
    Optional<Score> findByOrderId(Long id);

    Optional<Score> findScoreByOrder(Order order);
}
