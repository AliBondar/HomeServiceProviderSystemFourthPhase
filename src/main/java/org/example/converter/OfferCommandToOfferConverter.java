package org.example.converter;

import org.example.command.OfferCommand;
import org.example.entity.Offer;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.time.LocalDate;

public class OfferCommandToOfferConverter implements BaseConverter<OfferCommand, Offer> {
    @Override
    public Offer convert(OfferCommand offerCommand) throws NoSuchAlgorithmException {
        Offer offer = new Offer();
        offer.setExpert(offerCommand.getExpert());
        offer.setOrder(offerCommand.getOrder());
        offer.setOfferedPrice(offerCommand.getOfferedPrice());
        offer.setOfferedStartDate(offerCommand.getOfferedStartDate());
        offer.setOfferSignedDate(LocalDate.now());
        offer.setOfferedStartTime(Time.valueOf(offerCommand.getOfferedStartTime()));
        offer.setAccepted(false);
        offer.setExpertOfferedWorkDuration(offerCommand.getExpertOfferedWorkDuration());
        return offer;
    }

    @Override
    public OfferCommand convert(Offer offer) throws NoSuchAlgorithmException {
        return null;
    }
}
