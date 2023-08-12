package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.repository.ServiceRepository;
import org.example.service.ServiceService;
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


}
