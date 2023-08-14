package org.example.service;


import org.example.dto.ServiceDTO;
import org.example.dto.SubServiceDTO;
import org.example.entity.Service;
import org.example.entity.SubService;

import java.util.List;
import java.util.Optional;

public interface SubServiceService {

    void save(SubServiceDTO subServiceDTO);

    void delete(SubServiceDTO subServiceDTO);

    SubServiceDTO findById(Long id);

    List<SubServiceDTO> findAll();

    Optional<SubService> findByDescriptionAndService(String description, Service service);

    List<SubService> findByExpertId(Long id);

    List<SubService> findSubServicesByServiceId(Long id);

    Optional<SubService> findSubServiceByDescription(String description);

}
