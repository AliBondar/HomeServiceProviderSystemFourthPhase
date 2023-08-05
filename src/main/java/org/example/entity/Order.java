package org.example.entity;



import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.base.domain.BaseEntity;
import org.example.entity.enums.OrderStatus;
import org.example.entity.users.Client;
import org.example.entity.users.Expert;


import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Order extends BaseEntity<Long> {

    String description;

    LocalDate localDate;

    Time time;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    double paid;

    double clientOfferedPrice;

    int clientOfferedWorkDuration;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    List<Offer> offerList = new ArrayList<>();

    @ManyToOne
    Client client;

    @ManyToOne
    Expert expert;

    @ManyToOne
    SubService subService;

    @OneToOne
    Score score;


    @Override
    public String toString() {
        return "Order{" +
                "description='" + description + '\'' +
                ", localDate=" + localDate +
//                ", localTime=" + localTime +
                ", orderStatus=" + orderStatus +
                ", paid=" + paid +
//                ", client=" + client.getId() +
//                ", expert=" + expert.getId() +
//                ", Service=" + service.getName() +
//                ", Sub Service=" + subServiceList +
//                ", score=" + score.getScore() +
//                ", comment=" + comment.getText() +
                '}';
    }
}
