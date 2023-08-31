package org.example.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
}
