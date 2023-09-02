package org.example.dto.response;

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
public class ExpertResponseDTO {

    String firstName;

    String lastName;

    String email;

    int score;

    UserStatus userStatus;

    LocalDate signUpdate;

    Long serviceId;

    byte[] imageData;
}
