package org.example.command;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.Service;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubServiceCommand extends BaseCommand<Long>{

    double basePrice;

    String description;

    Service service;
}
