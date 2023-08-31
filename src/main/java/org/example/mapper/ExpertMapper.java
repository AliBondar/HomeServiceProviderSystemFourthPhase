package org.example.mapper;


import org.apache.commons.io.FileUtils;
import org.example.dto.ExpertDTO;
import org.example.dto.response.ExpertResponseDTO;
import org.example.entity.users.Expert;
import org.example.security.PasswordHash;
import org.mapstruct.Mapper;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Mapper
public class ExpertMapper implements BaseMapper<ExpertDTO, Expert> {

    @Override
    public Expert convert(ExpertDTO expertDTO) {
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
        File file = new File(expertDTO.getImageData());
        try {
            expert.setImageData(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expert;
    }

    @Override
    public ExpertDTO convert(Expert expert) {
        return null;
    }

    ExpertResponseDTO modelToExpertResponseDTO(Expert expert) {
        ExpertResponseDTO expertResponseDTO = new ExpertResponseDTO();
        expertResponseDTO.setFirstName(expertResponseDTO.getFirstName());
        expertResponseDTO.setLastName(expertResponseDTO.getLastName());
        expertResponseDTO.setEmail(expertResponseDTO.getEmail());
        return expertResponseDTO;
    }
}
