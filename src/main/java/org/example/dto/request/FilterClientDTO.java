package org.example.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.Wallet;
import org.example.entity.users.enums.UserStatus;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterClientDTO {

    String firstName;

    String lastName;

    Long orderId;

    @Email
    String email;

    UserStatus userStatus;

    LocalDate minSignUpDate;

    LocalDate maxSignUpDate;

    Integer minOrdersNumber;

    Integer maxOrdersNumber;

    Wallet wallet;
}
