package org.example.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.Order;
import org.example.entity.users.Client;
import org.example.entity.users.Expert;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreDTO extends BaseDTO<Long> {

    int score;

    String comment;

    Client client;

    Expert expert;

    Order order;

}
