package org.example.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.base.domain.BaseEntity;
import org.example.entity.users.Expert;


import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Offer extends BaseEntity<Long> {

    @ManyToOne
    Expert expert;

    @ManyToOne
    Order order;

    double offeredPrice;

    LocalDate offeredStartDate;

    int expertOfferedWorkDuration;

    LocalTime offerSignedTime;

    LocalDate offerSignedDate;

    boolean isAccepted;

    @Override
    public String toString() {
        return "Offer{" +
                "expert=" + expert +
                ", order=" + order +
                ", offeredPrice=" + offeredPrice +
                ", offeredStartDate=" + offeredStartDate +
                ", expertOfferedWorkDuration=" + expertOfferedWorkDuration +
                ", offerSignedTime=" + offerSignedTime +
                ", offerSignedDate=" + offerSignedDate +
                ", isAccepted=" + isAccepted +
                '}';
    }
}
