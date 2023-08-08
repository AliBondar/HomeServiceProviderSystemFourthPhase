package org.example.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.base.domain.BaseEntity;
import org.example.entity.enums.WorkTimeType;
import org.example.entity.users.Expert;


import java.sql.Time;
import java.time.LocalDate;

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

    Time offeredStartTime;

    WorkTimeType workTimeType;

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
                ", offeredStartTime=" + offeredStartTime +
                ", offerSignedDate=" + offerSignedDate +
                ", isAccepted=" + isAccepted +
                '}';
    }
}
