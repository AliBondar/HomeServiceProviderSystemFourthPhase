package org.example.mapper;

import org.example.dto.SubServiceDTO;
import org.example.entity.SubService;

import java.security.NoSuchAlgorithmException;

public class SubServiceMapper implements BaseMapper<SubServiceDTO, SubService> {

    @Override
    public SubService convert(SubServiceDTO subServiceDTO) {
        SubService subService = new SubService();
        subService.setBasePrice(subServiceDTO.getBasePrice());
        subService.setDescription(subServiceDTO.getDescription());
        subService.setService(subServiceDTO.getService());
        return subService;
    }

    @Override
    public SubServiceDTO convert(SubService subService) {
        SubServiceDTO subServiceDTO = new SubServiceDTO();
        subServiceDTO.setId(subService.getId());
        subServiceDTO.setBasePrice(subService.getBasePrice());
        subServiceDTO.setDescription(subService.getDescription());
        subServiceDTO.setService(subServiceDTO.getService());
        return subServiceDTO;
    }
}
