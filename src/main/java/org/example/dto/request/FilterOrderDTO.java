package org.example.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
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
