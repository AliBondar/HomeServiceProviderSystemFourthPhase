package org.example.repository;


import org.example.entity.Offer;
import org.example.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findOffersByOrder(Order order);

    @Query("select o from Offer o where o.order.id = :id")
    List<Offer> findOrdersByOrderId(Long id);

    @Query("select o from Offer o where o.id = :id and o.isAccepted = true")
    Optional<Offer> findAcceptedOfferByOrderId(Long id);
}
