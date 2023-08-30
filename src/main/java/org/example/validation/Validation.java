package org.example.validation;

import org.example.dto.OfferDTO;
import org.example.dto.OrderDTO;
import org.example.entity.SubService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class Validation {

    private final static String regexEmailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private final static String regexPasswordPattern = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";

    //Example username@domain.com
    public boolean emailPatternMatches(String emailAddress) {
        return !Pattern.compile(regexEmailPattern)
                .matcher(emailAddress)
                .matches();
    }

    //At least one special character, Capital world, number
    public boolean passwordPatternMatches(String password) {
        return !Pattern.compile(regexPasswordPattern)
                .matcher(password)
                .matches();
    }

    public boolean isDateValid(LocalDate localDate) {
        return localDate.isAfter(LocalDate.now());
    }

    public boolean isTimeValid(LocalTime localTime) {
        return localTime.isAfter(LocalTime.of(8,0)) && localTime.isBefore(LocalTime.of(21,0));
    }

    public boolean isOfferedPriceValid(OrderDTO orderCommand, SubService subService) {
        return orderCommand.getClientOfferedPrice() >= subService.getBasePrice();
    }

    public boolean isOfferedPriceValid(OfferDTO offerCommand, SubService subService) {
        return offerCommand.getOfferedPrice() >= subService.getBasePrice();
    }

    public boolean isScoreValid(int score){
        return score >= 1 && score <= 5;
    }
}
