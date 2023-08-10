package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Offer;
import org.example.entity.Order;
import org.example.repository.OfferRepository;
import org.example.service.OfferService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    @Override
    public List<Offer> findOffersByOrder(Order order) {
        List<Offer> offers = offerRepository.findOffersByOrder(order);
        offers.sort(Comparator.comparingDouble(Offer::getOfferedPrice).thenComparingInt(o -> o.getExpert().getScore()));
        return offers;
    }

    @Override
    public List<Offer> findOffersByOrderId(Long id) {
        return offerRepository.findOffersByOrderId(id);
    }

    @Override
    public Optional<Offer> findAcceptedOfferByOrderId(Long id) {
        try {
            return offerRepository.findAcceptedOfferByOrderId(id);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
