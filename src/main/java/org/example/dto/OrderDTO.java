package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate localDate;

    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime localTime;

    OrderStatus orderStatus;

    double paid;

    double clientOfferedPrice;

    int clientOfferedWorkDuration;

    Long clientId;

    Long subServiceId;
}
