package org.example.mapper;

import org.example.dto.SubServiceDTO;
import org.example.entity.SubService;

import java.security.NoSuchAlgorithmException;

public class SubServiceMapper implements BaseConverter<SubServiceDTO, SubService> {

    @Override
    public SubService convert(SubServiceDTO subServiceDTO) throws NoSuchAlgorithmException {
        SubService subService = new SubService();
        subService.setBasePrice(subServiceDTO.getBasePrice());
        subService.setDescription(subServiceDTO.getDescription());
        subService.setService(subServiceDTO.getService());
        return subService;
    }

    @Override
    public SubServiceDTO convert(SubService subService) throws NoSuchAlgorithmException {
        return null;
    }
}
