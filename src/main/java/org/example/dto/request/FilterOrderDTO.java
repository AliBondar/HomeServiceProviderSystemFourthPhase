package org.example.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.enums.OrderStatus;

import java.time.LocalDate;

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

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate minWorkDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate maxWorkDate;


}
