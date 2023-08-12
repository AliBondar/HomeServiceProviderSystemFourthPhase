package org.example.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.Service;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubServiceDTO extends BaseDTO<Long> {

    double basePrice;

    String description;

    Service service;
}
