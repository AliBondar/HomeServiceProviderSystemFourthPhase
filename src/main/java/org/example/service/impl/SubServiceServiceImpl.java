package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.command.SubServiceCommand;
import org.example.converter.SubServiceCommandToSubServiceConverter;
import org.example.entity.SubService;
import org.example.exception.DuplicatedSubServiceException;
import org.example.exception.NotFoundTheServiceException;
import org.example.repository.SubServiceRepository;
import org.example.service.AdminService;
import org.example.service.ServiceService;
import org.example.service.SubServiceService;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SubServiceServiceImpl implements SubServiceService {

    private final SubServiceRepository subServiceRepository;
    private final ServiceService serviceService;


    @Override
    public List<SubService> findWithServiceId(Long id) {
        try {
            return subServiceRepository.findWithServiceId(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void addSubService(SubServiceCommand subServiceCommand) {
        if (isSubServiceDuplicated(subServiceCommand.getDescription(), subServiceCommand.getService())) {
            throw new DuplicatedSubServiceException("Sub service already exists !");
        } else if (serviceService.findServiceByName(subServiceCommand.getService().getName()).isEmpty()) {
            throw new NotFoundTheServiceException("Couldn't find the service !");
        } else {
            SubServiceCommandToSubServiceConverter subServiceCommandToSubServiceConverter = new SubServiceCommandToSubServiceConverter();
            try {
                SubService subService = subServiceCommandToSubServiceConverter.convert(subServiceCommand);
               subServiceRepository.save(subService);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isSubServiceDuplicated(String description, org.example.entity.Service service) {
        return findByDescriptionAndService(description, service).isPresent();
    }

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
    public List<SubService> findByServiceId(Long id) {
        return subServiceRepository.findByServiceId(id);
    }

}
