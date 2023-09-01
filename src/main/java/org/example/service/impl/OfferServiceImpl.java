package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.OfferDTO;
import org.example.entity.Offer;
import org.example.entity.Order;
import org.example.entity.enums.OrderStatus;
import org.example.mapper.OfferMapper;
import org.example.repository.OfferRepository;
import org.example.repository.OrderRepository;
import org.example.service.OfferService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Transactional
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final OrderRepository orderRepository;
    private final OfferMapper offerMapper;

    @Override
    public void save(OfferDTO offerDTO) {
        Offer offer = offerMapper.convert(offerDTO);
        offerRepository.save(offer);
    }

    @Override
    public void delete(OfferDTO offerDTO) {
        Offer offer = offerMapper.convert(offerDTO);
        offerRepository.delete(offer);
    }

    @Override
    public OfferDTO findById(Long id) {
        Optional<Offer> offer = offerRepository.findById(id);
        return offer.map(offerMapper::convert).orElse(null);
    }

    @Override
    public List<OfferDTO> findAll() {
        List<Offer> offers = offerRepository.findAll();
        List<OfferDTO> offerDTOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(offers)) return null;
        else {
            for (Offer offer : offers) {
                offerDTOList.add(offerMapper.convert(offer));
            }
            return offerDTOList;
        }
    }

    @Override
    public List<Offer> findOffersByOrder(Order order) {
        List<Offer> offers = offerRepository.findOffersByOrder(order);
        offers.sort(Comparator.comparingDouble(Offer::getOfferedPrice).thenComparingInt(o -> o.getExpert().getScore()));
        return offers;
    }

    @Override
    public List<OfferDTO> findOffersByOrderId(Long id) {
        List<Offer> offers = offerRepository.findOffersByOrderId(id);
        List<OfferDTO> offerDTOList = new ArrayList<>();
        offers.sort(Comparator.comparingDouble(Offer::getOfferedPrice).thenComparingInt(o -> o.getExpert().getScore()));
        for (Offer offer : offers){
            offerDTOList.add(offerMapper.convert(offer));
        }
        return offerDTOList;
    }

    @Override
    public Optional<Offer> findAcceptedOfferByOrderId(Long id) {
        try {
            return offerRepository.findAcceptedOfferByOrderId(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Offer> findAcceptedOffersByExpertId(Long id) {
        return offerRepository.findAcceptedOffersByExpertId(id);
    }

    //TODO
    @Override
    public List<OfferDTO> findNewOffersByOrderId(Long id) {
        Predicate<OfferDTO> newOffer = offerDTO -> orderRepository.findById(offerDTO.getOrderId()).get().getOrderStatus() == OrderStatus.WAITING_FOR_EXPERT_OFFER;
        return null;
    }
}
