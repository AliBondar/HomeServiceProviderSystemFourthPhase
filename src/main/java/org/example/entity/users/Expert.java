package org.example.entity.users;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.entity.*;
import org.springframework.data.repository.cdi.Eager;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Expert extends User {

    byte[] imageData;

    int score;

    @ManyToOne
    Service service;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<SubService> subServiceList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id")
    List<Score> scoreList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id")
    List<Order> orderList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id")
    List<Offer> offerList = new ArrayList<>();

    @Override
    public String toString() {
        return "Expert{" +
                "score=" + score +
                ", service=" + service.getName() +
                ", subServiceList=" + subServiceList +
                '}';
    }
}
