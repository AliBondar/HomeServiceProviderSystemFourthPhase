package org.example.converter;

import org.example.command.ServiceCommand;
import org.example.entity.Service;

import java.security.NoSuchAlgorithmException;

public class ServiceCommandToServiceConverter implements BaseConverter<ServiceCommand, Service> {

    @Override
    public Service convert(ServiceCommand serviceCommand) throws NoSuchAlgorithmException {
        Service service = new Service();
        service.setName(serviceCommand.getName());
        return service;
    }

    @Override
    public ServiceCommand convert(Service service) throws NoSuchAlgorithmException {
        return null;
    }
}
