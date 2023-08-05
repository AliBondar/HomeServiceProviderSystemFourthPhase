package org.example.command;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.SubService;
import org.example.entity.users.Client;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCommand extends BaseCommand<Long>{

    String description;

    LocalDate localDate;

    LocalTime localTime;

    double clientOfferedPrice;

    int clientOfferedWorkDuration;

    Client client;

    SubService subService;
}
