package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.SubService;
import org.example.repository.SubServiceRepository;
import org.example.service.ServiceService;
import org.example.service.SubServiceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SubServiceServiceImpl implements SubServiceService {

    private final SubServiceRepository subServiceRepository;
    private final ServiceService serviceService;

    @Override
    public Optional<SubService> findByDescriptionAndService(String description, org.example.entity.Service service) {
        try {
            return subServiceRepository.findByDescriptionAndService(description, service);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<SubService> findByExpertId(Long id) {
        return subServiceRepository.findByExpertId(id);
    }

    @Override
    public List<SubService> findSubServicesByServiceId(Long id) {
        return subServiceRepository.findSubServicesByServiceId(id);
    }

    @Override
    public Optional<SubService> findSubServiceByDescription(String description) {
        try {
         return subServiceRepository.findSubServiceByDescription(description);
        }catch (Exception e) {
            return Optional.empty();
        }
    }

}
