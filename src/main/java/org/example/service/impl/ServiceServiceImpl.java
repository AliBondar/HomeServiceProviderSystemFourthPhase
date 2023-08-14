package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.ServiceDTO;
import org.example.mapper.OfferMapper;
import org.example.mapper.OrderMapper;
import org.example.mapper.ServiceMapper;
import org.example.repository.ServiceRepository;
import org.example.service.ServiceService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public void save(ServiceDTO serviceDTO) {
        org.example.entity.Service service = serviceMapper.convert(serviceDTO);
        serviceRepository.save(service);
    }

    @Override
    public void delete(ServiceDTO serviceDTO) {
        org.example.entity.Service service = serviceMapper.convert(serviceDTO);
        serviceRepository.delete(service);
    }

    @Override
    public ServiceDTO findById(Long id) {
        Optional<org.example.entity.Service> service = serviceRepository.findById(id);
        return service.map(serviceMapper::convert).orElse(null);
    }

    @Override
    public List<ServiceDTO> findAll() {
        List<org.example.entity.Service> services = serviceRepository.findAll();
        List<ServiceDTO> serviceDTOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(services)) return null;
        else {
            for (org.example.entity.Service service : services){
                serviceDTOList.add(serviceMapper.convert(service));
            }
            return serviceDTOList;
        }
    }

    @Override
    public Optional<org.example.entity.Service> findServiceByName(String name) {
        try {
            return serviceRepository.findServiceByName(name);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


}
