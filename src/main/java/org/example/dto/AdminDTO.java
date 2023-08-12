package org.example.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.example.dto.UserDTO;

@Setter
@Getter
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminDTO extends UserDTO {


}
