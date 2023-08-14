package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.OfferDTO;
import org.example.entity.Offer;
import org.example.entity.Order;
import org.example.mapper.OfferMapper;
import org.example.repository.OfferRepository;
import org.example.service.OfferService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
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
    public List<Offer> findOffersByOrderId(Long id) {
        return offerRepository.findOffersByOrderId(id);
    }

    @Override
    public Optional<Offer> findAcceptedOfferByOrderId(Long id) {
        try {
            return offerRepository.findAcceptedOfferByOrderId(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
