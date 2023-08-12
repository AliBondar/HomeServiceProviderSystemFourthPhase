package org.example.mapper;

import org.example.dto.OfferDTO;
import org.example.entity.Offer;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.time.LocalDate;

public class OfferMapper implements BaseConverter<OfferDTO, Offer> {
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
        return null;
    }
}
