package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ClientDTO;
import org.example.dto.ExpertDTO;
import org.example.dto.ServiceDTO;
import org.example.dto.SubServiceDTO;
import org.example.entity.users.Expert;
import org.example.entity.users.User;
import org.example.entity.users.enums.UserStatus;
import org.example.service.AdminService;
import org.example.service.ExpertService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ExpertService expertService;

    @PostMapping("/add-expert-to-sub-service/{expertId}/{subServiceId}")
    public void addExpertToSubService(@PathVariable Long expertId,@PathVariable Long subServiceId) {
        adminService.addExpertToSubService(expertId, subServiceId);
    }

    @PostMapping("/remove-expert-from-sub-service/{expertId}/{subServiceId}")
    public void removeExpertFromSubService(@PathVariable Long expertId, @PathVariable Long subServiceId) {
        adminService.removeExpertFromSubService(expertId, subServiceId);
    }

    @PutMapping("/edit-expert-status/{expertId}/{userStatus}")
    public void editExpertStatus(@PathVariable Long expertId, @PathVariable UserStatus userStatus) {
        adminService.editExpertStatus(expertId, userStatus);
    }

    @PutMapping("/edit-sub-service/{id}/{newBasePrice}/{newDescription}")
    public void editSubService(@PathVariable Long id,@PathVariable double newBasePrice,@PathVariable String newDescription) {
        adminService.editSubService(id, newBasePrice, newDescription);
    }

    @PostMapping("/add-service")
    public void addService(@RequestBody ServiceDTO serviceDTO) {
        adminService.addService(serviceDTO);
    }

    @PostMapping("/add-sub-service")
    public void addSubService(@RequestBody SubServiceDTO subServiceDTO) {
        adminService.addSubService(subServiceDTO);
    }

    @GetMapping("/show-expert-by-email/{email}")
    public Expert findExpertByEmail(@PathVariable String email) {
        return expertService.findExpertByEmail(email).get();
    }

    @GetMapping("/show-experts-by-user-status")
    public List<Expert> findExpertsByUserStatus(UserStatus userStatus) {
       return expertService.findExpertsByUserStatus(userStatus);
    }

    @PostMapping("/filter-clients")
    public List<ClientDTO> filterClient(@RequestBody ClientDTO clientDTO) {
        return adminService.filterClient(clientDTO);
    }

    @PostMapping("/filter-experts")
    public List<ExpertDTO> filterExpert(@RequestBody ExpertDTO expertDTO) {
        return adminService.filterExpert(expertDTO);
    }
}
