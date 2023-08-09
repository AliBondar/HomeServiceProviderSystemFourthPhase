package org.example.service;


import org.example.entity.Service;
import org.example.entity.SubService;

import java.util.List;
import java.util.Optional;

public interface SubServiceService {

    Optional<SubService> findByDescriptionAndService(String description, Service service);

    List<SubService> findByExpertId(Long id);

    List<SubService> findSubServicesByServiceId(Long id);

    Optional<SubService> findSubServiceByDescription(String description);

}
