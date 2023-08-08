package org.example.service;


import org.example.entity.Offer;
import org.example.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OfferService {
    List<Offer> findOffersByOrder(Order order);

    List<Offer> findOffersByOrderId(Long id);

    Optional<Offer> findAcceptedOfferByOrderId(Long id);
}
