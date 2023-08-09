package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.command.ExpertSignUpCommand;
import org.example.command.ServiceCommand;
import org.example.command.SubServiceCommand;
import org.example.converter.SubServiceCommandToSubServiceConverter;
import org.example.entity.SubService;
import org.example.entity.users.Admin;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;
import org.example.exception.*;
import org.example.repository.AdminRepository;
import org.example.repository.ExpertRepository;
import org.example.repository.ServiceRepository;
import org.example.repository.SubServiceRepository;
import org.example.security.PasswordHash;
import org.example.service.AdminService;
import org.example.service.ServiceService;
import org.example.service.SubServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ExpertRepository expertRepository;
    private final SubServiceRepository subServiceRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;


    @Override
    public Optional<Admin> findAdminByEmail(String email) {
        try {
            return adminRepository.findAdminByEmail(email);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    PasswordHash passwordHash = new PasswordHash();

    @Override
    public Optional<Admin> findAdminByEmailAndPassword(String email, String password) {
        try {
            return adminRepository.findAdminByEmailAndPassword(email, passwordHash.createHashedPassword(password));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public void addExpertToSubService(ExpertSignUpCommand expertSignUpCommand, SubServiceCommand subServiceCommand) {

    }

    @Override
    public void addExpertToSubService(Long expertId, Long subServiceId) {
        if (expertRepository.findById(expertId).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        } else if (subServiceRepository.findById(subServiceId).isEmpty()) {
            throw new NotFoundTheSubServiceException("Couldn't find the sub service !");
        } else if (expertRepository.findById(expertId).get().getSubServiceList().contains(subServiceRepository.findById(subServiceId).get())) {
            throw new DuplicatedSubServiceException("Expert is already a member of this sub service !");
        } else if ((!Objects.equals(expertRepository.findById(expertId).get().getService().getName(), subServiceRepository.findById(subServiceId).get().getService().getName()))) {
            throw new NotInServiceException("Expert is not a member of this service !");
        } else if (!expertRepository.findById(expertId).get().getUserStatus().equals(UserStatus.CONFIRMED)) {
            throw new UserConfirmationException("User is not confirmed yet.");
        } else {
            Expert expert = expertRepository.findById(expertId).get();
            SubService subService = subServiceRepository.findById(subServiceId).get();
            expert.getSubServiceList().add(subService);
            subService.getExpertList().add(expert);
            expertRepository.save(expert);
            subServiceRepository.save(subService);
        }
    }

    @Override
    public void removeExpertFromSubService(Long expertId, Long subServiceId) {
        if (expertRepository.findById(expertId).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        } else if (subServiceRepository.findById(subServiceId).isEmpty()) {
            throw new NotFoundTheSubServiceException("Couldn't find the sub service !");
        } else if (!expertRepository.findById(expertId).get().getSubServiceList().contains(subServiceRepository.findById(subServiceId).get())) {
            throw new NotInSubServiceException("Expert already is not a member of this sub service !");
        }else {
            Expert expert = expertRepository.findById(expertId).get();
            SubService subService = subServiceRepository.findById(subServiceId).get();
            expert.getSubServiceList().remove(subService);
            subService.getExpertList().remove(expert);
            expertRepository.save(expert);
            subServiceRepository.save(subService);
        }
    }

    @Override
    public void editExpertStatus(Long expertId, UserStatus userStatus) {
        if (expertRepository.findById(expertId).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        }else {
            Expert expert = expertRepository.findById(expertId).get();
            expert.setUserStatus(userStatus);
            expertRepository.save(expert);
        }
    }

    @Override
    public void editSubService(Long id, double newBasePrice, String newDescription) {
        if (subServiceRepository.findById(id).isEmpty()){
            throw new NotFoundTheSubServiceException("Couldn't find the sub service !");
        }else {
            SubService subService = subServiceRepository.findById(id).get();
            subService.setBasePrice(newBasePrice);
            subService.setDescription(newDescription);
            subServiceRepository.save(subService);
        }
    }

    @Override
    public void addService(ServiceCommand serviceCommand) {
        if (isServiceDuplicated(serviceCommand.getName())) {
            throw new DuplicatedServiceException("Service already exists !");
        } else {
            org.example.entity.Service service = new org.example.entity.Service();
            service.setName(serviceCommand.getName());
            serviceRepository.save(service);
        }
    }

    @Override
    public boolean isServiceDuplicated(String name) {
        return serviceService.findServiceByName(name).isPresent();
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
        return subServiceService.findByDescriptionAndService(description, service).isPresent();
    }

}
