package org.example.service;

import org.example.dto.OrderDTO;
import org.example.dto.ServiceDTO;
import org.example.entity.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceService {

    void save(ServiceDTO serviceDTO);

    void delete(ServiceDTO serviceDTO);

    ServiceDTO findById(Long id);

    List<ServiceDTO> findAll();

    Optional<Service> findServiceByName(String name);
}
