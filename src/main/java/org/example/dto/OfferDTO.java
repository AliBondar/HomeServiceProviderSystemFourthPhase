package org.example.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.Order;
import org.example.entity.enums.WorkTimeType;
import org.example.entity.users.Expert;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OfferDTO extends BaseDTO<Long> {

    Expert expert;

    Order order;

    double offeredPrice;

    LocalDate offeredStartDate;

    int expertOfferedWorkDuration;

    LocalTime offeredStartTime;

    WorkTimeType workTimeType;

    LocalDate offerSignedDate;

    boolean isAccepted;
}
