package org.example.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.example.base.domain.BaseEntity;



@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet extends BaseEntity<Long> {

    double balance;

    @Override
    public String toString() {
        return "Wallet{" +
                "balance=" + balance +
                '}';
    }
}
