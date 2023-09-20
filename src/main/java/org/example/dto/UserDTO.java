package org.example.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.Wallet;
import org.example.entity.users.enums.Role;
import org.example.entity.users.enums.UserStatus;


import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO extends BaseDTO<Long> {

    String firstName;

    String lastName;

    @Email
    String email;

    String password;

    Role role;

    UserStatus userStatus;

    LocalDate signUpDate;

    Wallet wallet;
}
