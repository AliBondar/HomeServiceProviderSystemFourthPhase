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

@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Service extends BaseEntity<Long> {

    String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    List<Expert> expertList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    List<SubService> subServiceList = new ArrayList<>();

    public Service(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                '}';
    }
}
