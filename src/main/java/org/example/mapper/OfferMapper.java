package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.OfferDTO;
import org.example.entity.Offer;
import org.example.repository.ExpertRepository;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class OfferMapper implements BaseMapper<OfferDTO, Offer> {

    final ExpertRepository expertRepository;
    final OrderRepository orderRepository;

    @Override
    public Offer convert(OfferDTO offerDTO) {
        Offer offer = new Offer();
        offer.setExpert(expertRepository.findById(offerDTO.getExpertId()).get());
        offer.setOrder(orderRepository.findById(offerDTO.getOrderId()).get());
        offer.setOfferedPrice(offerDTO.getOfferedPrice());
        offer.setOfferedStartDate(offerDTO.getOfferedStartDate());
        offer.setOfferSignedDate(LocalDate.now());
        offer.setOfferedStartTime(Time.valueOf(offerDTO.getOfferedStartTime()));
        offer.setWorkTimeType(offerDTO.getWorkTimeType());
        offer.setAccepted(false);
        offer.setExpertOfferedWorkDuration(offerDTO.getExpertOfferedWorkDuration());
        return offer;
    }

    @Override
    public OfferDTO convert(Offer offer) {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setExpertId(offer.getExpert().getId());
        offerDTO.setOrderId(offer.getOrder().getId());
        offerDTO.setOfferedPrice(offer.getOfferedPrice());
        offerDTO.setOfferedStartDate(offer.getOfferedStartDate());
        offerDTO.setOfferSignedDate(offer.getOfferSignedDate());
        offerDTO.setOfferedStartTime(offer.getOfferedStartTime().toLocalTime());
        offerDTO.setWorkTimeType(offer.getWorkTimeType());
        offerDTO.setAccepted(offer.isAccepted());
        offerDTO.setExpertOfferedWorkDuration(offer.getExpertOfferedWorkDuration());
        return offerDTO;
    }
}
