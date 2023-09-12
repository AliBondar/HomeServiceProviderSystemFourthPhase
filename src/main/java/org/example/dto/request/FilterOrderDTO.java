package org.example.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.enums.OrderStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterOrderDTO {

    String description;

    OrderStatus orderStatus;

    Long subServiceId;

    Long serviceId;

    Double minClientOfferedPrice;

    Double maxClientOfferedPrice;

    Integer minClientOfferedWorkDuration;

    Integer maxClientOfferedWorkDuration;


}
