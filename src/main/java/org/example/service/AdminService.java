package org.example.service;


import org.example.command.ExpertSignUpCommand;
import org.example.command.ServiceCommand;
import org.example.command.SubServiceCommand;
import org.example.entity.Service;
import org.example.entity.users.Admin;
import org.example.entity.users.enums.UserStatus;

import java.util.Optional;

public interface AdminService {

    Optional<Admin> findAdminByEmail(String email);

    Optional<Admin> findAdminByEmailAndPassword(String email, String password);

    void addExpertToSubService(ExpertSignUpCommand expertSignUpCommand, SubServiceCommand subServiceCommand);

    void addExpertToSubService(Long expertId, Long subServiceId);

    void removeExpertFromSubService(Long expertId, Long subServiceId);

    void editExpertStatus(Long expertId, UserStatus userStatus);

    void editSubService(Long id, double newBasePrice, String newDescription);

    void addService(ServiceCommand serviceCommand);

    boolean isServiceDuplicated(String name);


    void addSubService(SubServiceCommand subServiceCommand);

    boolean isSubServiceDuplicated(String description, Service service);
}
