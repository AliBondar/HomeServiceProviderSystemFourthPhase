package org.example.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.base.domain.BaseEntity;
import org.example.entity.users.Client;
import org.example.entity.users.Expert;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Score extends BaseEntity<Long> {

    @Min(1)
    @Max(5)
    int score;

    @ColumnDefault("no comment")
    String comment;

    @ManyToOne
    Expert expert;

    @ManyToOne
    Client client;

    @OneToOne
    Order order;

    @Override
    public String toString() {
        return "Score{" +
                "score=" + score +
                ", expert=" + expert +
                ", client=" + client +
                ", order=" + order +
                '}';
    }
}
