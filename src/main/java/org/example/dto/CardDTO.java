package org.example.dto;


import jakarta.persistence.Transient;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardDTO extends BaseDTO<Long>{

    @Pattern(regexp = "^[0-9]{16}$", message = "Card number must be 16 digits")
    String cardNumber;

    @Pattern(regexp = "^[0-9]{3,4}$", message = "invalid format for CVV2")
    String cvv2;

    String expireYear;

    String expireMonth;

    String password;

    Long orderId;

    @Transient
    private String captcha;
    @Transient
    private String hidden;
    @Transient
    private String image;

}
