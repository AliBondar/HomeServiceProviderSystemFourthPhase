package org.example.converter;

import org.apache.commons.io.FileUtils;
import org.example.command.ExpertSignUpCommand;
import org.example.entity.users.Expert;
import org.example.security.PasswordHash;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ExpertSIgnUpCommandToExpertConverter implements BaseConverter<ExpertSignUpCommand, Expert> {

    @Override
    public Expert convert(ExpertSignUpCommand expertSignUpCommand) throws NoSuchAlgorithmException {
        Expert expert = new Expert();
        PasswordHash passwordHash = new PasswordHash();
        expert.setFirstName(expertSignUpCommand.getFirstName());
        expert.setLastName(expertSignUpCommand.getLastName());
        expert.setEmail(expertSignUpCommand.getEmail());
        expert.setPassword(passwordHash.createHashedPassword(expertSignUpCommand.getPassword()));
        expert.setSignUpDate(expertSignUpCommand.getSignUpDate());
        expert.setScore(expertSignUpCommand.getScore());
        expert.setUserStatus(expertSignUpCommand.getUserStatus());
        expert.setService(expertSignUpCommand.getService());
        try {
            expert.setImagedData(FileUtils.readFileToByteArray(expertSignUpCommand.getImageData()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expert;
    }

    @Override
    public ExpertSignUpCommand convert(Expert expert) throws NoSuchAlgorithmException {
        return null;
    }
}
