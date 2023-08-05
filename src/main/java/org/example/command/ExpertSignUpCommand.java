package org.example.command;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.entity.Service;

import java.io.File;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpertSignUpCommand extends UserSignUpCommand{


    File imageData;

    int score;

    Service service;
}
