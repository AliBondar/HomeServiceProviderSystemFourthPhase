package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.Order;
import org.example.entity.users.Client;
import org.example.entity.users.Expert;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreDTO extends BaseDTO<Long> {

    @Min(1)
    @Max(5)
    int score;

    @ColumnDefault("no comment")
    String comment;

    Client client;

    Expert expert;

    Order order;

    Long orderId;



}
