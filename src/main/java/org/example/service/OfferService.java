package org.example.service;


import org.example.dto.ExpertDTO;
import org.example.dto.OfferDTO;
import org.example.entity.Offer;
import org.example.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OfferService {

    void save(OfferDTO offerDTO);

    void delete(OfferDTO offerDTO);

    OfferDTO findById(Long id);

    List<OfferDTO> findAll();

    List<Offer> findOffersByOrder(Order order);

    List<OfferDTO> findOffersByOrderId(Long id);

    Optional<Offer> findAcceptedOfferByOrderId(Long id);

    List<Offer> findAcceptedOffersByExpertId(Long id);

    List<OfferDTO> findNewOffersByOrderId(Long id);
}
