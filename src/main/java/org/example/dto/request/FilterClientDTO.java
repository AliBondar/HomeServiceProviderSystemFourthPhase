package org.example.dto.request;

import jakarta.validation.constraints.Email;
import org.example.entity.Wallet;
import org.example.entity.users.enums.UserStatus;

import java.time.LocalDate;

public class FilterClientDTO {

    String firstName;

    String lastName;

    @Email
    String email;

    UserStatus userStatus;

    LocalDate signUpDate;

    Wallet wallet;
}
