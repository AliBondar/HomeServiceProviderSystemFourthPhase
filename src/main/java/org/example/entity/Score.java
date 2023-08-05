package org.example.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.base.domain.BaseEntity;
import org.example.entity.users.Client;
import org.example.entity.users.Expert;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Score extends BaseEntity<Long> {

    int score;

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
