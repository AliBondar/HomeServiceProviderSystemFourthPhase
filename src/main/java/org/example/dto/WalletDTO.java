package org.example.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletDTO extends BaseDTO<Long>{

    double balance;
}
