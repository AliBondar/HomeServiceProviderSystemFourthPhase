package org.example.mapper;

import org.example.dto.OfferDTO;
import org.example.entity.Offer;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.time.LocalDate;

public class OfferMapper implements BaseMapper<OfferDTO, Offer> {
    @Override
    public Offer convert(OfferDTO offerDTO) throws NoSuchAlgorithmException {
        Offer offer = new Offer();
        offer.setExpert(offerDTO.getExpert());
        offer.setOrder(offerDTO.getOrder());
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
    public OfferDTO convert(Offer offer) throws NoSuchAlgorithmException {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setExpert(offer.getExpert());
        offerDTO.setOrder(offer.getOrder());
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
