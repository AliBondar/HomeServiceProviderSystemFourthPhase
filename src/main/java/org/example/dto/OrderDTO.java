package org.example.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.SubService;
import org.example.entity.enums.OrderStatus;
import org.example.entity.users.Client;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO extends BaseDTO<Long> {

    String description;

    LocalDate localDate;

    LocalTime localTime;

    OrderStatus orderStatus;

    double paid;

    double clientOfferedPrice;

    int clientOfferedWorkDuration;

    Client client;

    SubService subService;
}
