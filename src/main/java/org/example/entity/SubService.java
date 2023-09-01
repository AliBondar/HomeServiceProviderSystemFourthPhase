package org.example.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.base.domain.BaseEntity;
import org.example.entity.users.Expert;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubService extends BaseEntity<Long> {

    String description;

    double basePrice;

    @ManyToOne
    Service service;

    @ManyToMany(mappedBy = "subServiceList", fetch = FetchType.EAGER)
    List<Expert> expertList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_service_id")
    List<Order> orderList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubService that = (SubService) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    public SubService(double basePrice, String description, Service service) {
        this.basePrice = basePrice;
        this.description = description;
        this.service = service;
    }

    @Override
    public String toString() {
        return "SubService{" +
                "description='" + description + '\'' +
                ", basePrice=" + basePrice +
                ", service=" + service.getName() +
                '}';
    }
}
