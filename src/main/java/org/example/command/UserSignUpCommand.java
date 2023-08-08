package org.example.command;


import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.users.enums.UserStatus;


import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSignUpCommand extends BaseCommand<Long> {

    String firstName;

    String lastName;

    @Email
    String email;

    String password;

    UserStatus userStatus;

    LocalDate signUpDate;
}
