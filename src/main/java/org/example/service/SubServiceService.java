package org.example.service;


import org.example.command.SubServiceCommand;
import org.example.entity.Service;
import org.example.entity.SubService;

import java.util.List;
import java.util.Optional;

public interface SubServiceService {
    List<SubService> findWithServiceId(Long id);

    void addSubService(SubServiceCommand subServiceCommand);

    boolean isSubServiceDuplicated(String description, Service service);

    Optional<SubService> findByDescriptionAndService(String description, Service service);

    List<SubService> findByExpertId(Long id);

    List<SubService> findByServiceId(Long id);
}
