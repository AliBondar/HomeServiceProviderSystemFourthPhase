package org.example.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.ClientDTO;
import org.example.dto.ExpertDTO;
import org.example.dto.ServiceDTO;
import org.example.dto.SubServiceDTO;
import org.example.dto.request.EmailDTO;
import org.example.dto.response.ExpertResponseDTO;
import org.example.entity.users.Expert;
import org.example.entity.users.User;
import org.example.entity.users.enums.UserStatus;
import org.example.service.AdminService;
import org.example.service.ClientService;
import org.example.service.ExpertService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ExpertService expertService;
    private final ClientService clientService;

    @PostMapping("/add-expert-to-sub-service/{expertId}/{subServiceId}")
    public void addExpertToSubService(@PathVariable Long expertId, @PathVariable Long subServiceId) {
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
    public void editSubService(@PathVariable Long id, @PathVariable double newBasePrice, @PathVariable String newDescription) {
        adminService.editSubService(id, newBasePrice, newDescription);
    }

    @PostMapping("/add-service")
    public void addService(@RequestBody ServiceDTO serviceDTO) {
        adminService.addService(serviceDTO);
    }

    @Transactional
    @PostMapping("/add-sub-service")
    public void addSubService(@RequestBody SubServiceDTO subServiceDTO) {
        adminService.addSubService(subServiceDTO);
    }

    @GetMapping("/show-expert-by-email/{email}")
    public ExpertResponseDTO findExpertByEmail(@PathVariable String email) {
        return expertService.findExpertDTOByEmail(email);
    }

    @GetMapping("/show-experts-by-user-status/{strUserStatus}")
    public List<Expert> findExpertsByUserStatus(@PathVariable String strUserStatus) {
//        if (Objects.equals(strUserStatus, "CONFIRMED") || Objects.equals(strUserStatus, "WAITING")
//                || Objects.equals(strUserStatus, "DISABLED") || Objects.equals(strUserStatus, "NEW")){
//            UserStatus userStatus = null;
//            if (Objects.equals(strUserStatus, "CONFIRMED")) userStatus = UserStatus.CONFIRMED;
//            else if (Objects.equals(strUserStatus, "WAITING")) userStatus = UserStatus.WAITING;
//            else if (Objects.equals(strUserStatus, "DISABLED")) userStatus = UserStatus.DISABLED;
//            else if (Objects.equals(strUserStatus, "NEW")) userStatus = UserStatus.NEW;
//            return expertService.findExpertsByUserStatus(userStatus);
//        } else return null;
//        if (Objects.equals(strUserStatus, "2"))
        return expertService.findExpertsByUserStatus(UserStatus.CONFIRMED);
    }

    @PostMapping("/filter-clients")
    public List<ClientDTO> filterClient(@RequestBody ClientDTO clientDTO) {
        return clientService.filterClient(clientDTO);
    }

    @PostMapping("/filter-experts")
    public List<ExpertDTO> filterExpert(@RequestBody ExpertDTO expertDTO) {
        return expertService.filterExpert(expertDTO);
    }
}
