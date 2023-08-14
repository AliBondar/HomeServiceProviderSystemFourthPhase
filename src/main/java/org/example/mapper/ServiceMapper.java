package org.example.mapper;

import org.example.dto.ServiceDTO;
import org.example.entity.Service;

import java.security.NoSuchAlgorithmException;

public class ServiceMapper implements BaseMapper<ServiceDTO, Service> {

    @Override
    public Service convert(ServiceDTO serviceDTO) throws NoSuchAlgorithmException {
        Service service = new Service();
        service.setName(serviceDTO.getName());
        return service;
    }

    @Override
    public ServiceDTO convert(Service service) throws NoSuchAlgorithmException {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName(service.getName());
        return serviceDTO;
    }
}
