package org.example.converter;

import org.example.command.SubServiceCommand;
import org.example.entity.SubService;

import java.security.NoSuchAlgorithmException;

public class SubServiceCommandToSubServiceConverter implements BaseConverter<SubServiceCommand, SubService> {

    @Override
    public SubService convert(SubServiceCommand subServiceCommand) throws NoSuchAlgorithmException {
        SubService subService = new SubService();
        subService.setBasePrice(subServiceCommand.getBasePrice());
        subService.setDescription(subServiceCommand.getDescription());
        subService.setService(subServiceCommand.getService());
        return subService;
    }

    @Override
    public SubServiceCommand convert(SubService subService) throws NoSuchAlgorithmException {
        return null;
    }
}
