package org.example.service;

import org.example.command.ServiceCommand;
import org.example.entity.Service;

import java.util.Optional;

public interface ServiceService {

    void addService(ServiceCommand serviceCommand);

    boolean isServiceDuplicated(String name);

    Optional<Service> findServiceByName(String name);
}
