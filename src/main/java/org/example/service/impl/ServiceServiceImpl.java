package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.command.ServiceCommand;
import org.example.exception.DuplicatedServiceException;
import org.example.repository.ServiceRepository;
import org.example.service.ServiceService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public Optional<org.example.entity.Service> findServiceByName(String name) {
        try {
            return serviceRepository.findServiceByName(name);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void addService(ServiceCommand serviceCommand) {
        if (isServiceDuplicated(serviceCommand.getName())) {
            throw new DuplicatedServiceException("Service already exists !");
        } else {
            org.example.entity.Service service = new org.example.entity.Service();
            service.setName(serviceCommand.getName());
            serviceRepository.save(service);
        }
    }

    @Override
    public boolean isServiceDuplicated(String name) {
        return this.findServiceByName(name).isPresent();
    }
}
