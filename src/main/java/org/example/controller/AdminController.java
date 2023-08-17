package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ServiceDTO;
import org.example.dto.SubServiceDTO;
import org.example.entity.users.enums.UserStatus;
import org.example.service.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    public void addExpertToSubService(Long expertId, Long subServiceId){
        adminService.addExpertToSubService(expertId, subServiceId);
    }

    public void removeExpertFromSubService(Long expertId, Long subServiceId){
        adminService.removeExpertFromSubService(expertId, subServiceId);
    }

    public void editExpertStatus(Long expertId, UserStatus userStatus){
        adminService.editExpertStatus(expertId, userStatus);
    }

    public void editSubService(Long id, double newBasePrice, String newDescription){
        adminService.editSubService(id, newBasePrice, newDescription);
    }

    public void addService(ServiceDTO serviceDTO){
        adminService.addService(serviceDTO);
    }

    public void addSubService(SubServiceDTO subServiceDTO){
        adminService.addSubService(subServiceDTO);
    }
}
