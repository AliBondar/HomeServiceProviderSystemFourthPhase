package org.example.mapper;


import org.apache.commons.io.FileUtils;
import org.example.dto.ExpertDTO;
import org.example.entity.users.Expert;
import org.example.security.PasswordHash;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ExpertMapper implements BaseMapper<ExpertDTO, Expert> {

    @Override
    public Expert convert(ExpertDTO expertDTO)  {
        Expert expert = new Expert();
        PasswordHash passwordHash = new PasswordHash();
        expert.setFirstName(expertDTO.getFirstName());
        expert.setLastName(expertDTO.getLastName());
        expert.setEmail(expertDTO.getEmail());
        try {
            expert.setPassword(passwordHash.createHashedPassword(expertDTO.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        expert.setSignUpDate(expertDTO.getSignUpDate());
        expert.setScore(expertDTO.getScore());
        expert.setUserStatus(expertDTO.getUserStatus());
        expert.setService(expertDTO.getService());
        expert.setWallet(expertDTO.getWallet());
        try {
            expert.setImageData(FileUtils.readFileToByteArray(expertDTO.getImageData()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expert;
    }

    @Override
    public ExpertDTO convert(Expert expert) {
        return null;
    }
}
