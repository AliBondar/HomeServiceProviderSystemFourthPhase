package org.example.command;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.Order;
import org.example.entity.users.Expert;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OfferCommand extends BaseCommand<Long> {

    Expert expert;

    Order order;

    double offeredPrice;

    LocalDate offeredStartDate;

    int expertOfferedWorkDuration;

    LocalTime offeredStartTime;

    LocalDate offerSignedDate;

    boolean isAccepted;
}
