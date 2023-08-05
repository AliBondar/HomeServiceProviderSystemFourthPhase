package org.example.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminCommand extends UserSignUpCommand{


}
