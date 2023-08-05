package org.example.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.base.domain.BaseEntity;



@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet extends BaseEntity<Long> {

    double balance;

}
