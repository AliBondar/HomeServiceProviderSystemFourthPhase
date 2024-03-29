package org.example.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.dto.request.FilterClientDTO;
import org.example.dto.request.FilterOrderDTO;
import org.example.dto.response.ExpertResponseDTO;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;
import org.example.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ExpertService expertService;
    private final ClientService clientService;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;

    @PostMapping("/add-expert-to-sub-service/{expertId}/{subServiceId}")
    public ResponseEntity<String> addExpertToSubService(@PathVariable Long expertId, @PathVariable Long subServiceId) {
        adminService.addExpertToSubService(expertId, subServiceId);
        return ResponseEntity.ok().body("Expert has been added to the sub service successfully.");
    }

    @PutMapping("/remove-expert-from-sub-service/{expertId}/{subServiceId}")
    public ResponseEntity<String> removeExpertFromSubService(@PathVariable Long expertId, @PathVariable Long subServiceId) {
        adminService.removeExpertFromSubService(expertId, subServiceId);
        return ResponseEntity.ok().body("Expert has been removed from sub service.");
    }

    @PutMapping("/edit-expert-status/{expertId}/{userStatus}")
    public ResponseEntity<String> editExpertStatus(@PathVariable Long expertId, @PathVariable UserStatus userStatus) {
        adminService.editExpertStatus(expertId, userStatus);
        return ResponseEntity.ok().body("Expert status has been changed.");
    }

    @PutMapping("/confirm-expert/{expertId}")
    public ResponseEntity<String> confirmExpert(@PathVariable Long expertId){
        adminService.editExpertStatus(expertId, UserStatus.CONFIRMED);
        return ResponseEntity.ok().body("Expert confirmed.");
    }

    @PutMapping("/edit-sub-service/{id}/{newBasePrice}/{newDescription}")
    public ResponseEntity<String> editSubService(@PathVariable Long id, @PathVariable double newBasePrice, @PathVariable String newDescription) {
        adminService.editSubService(id, newBasePrice, newDescription);
        return ResponseEntity.ok().body("Sub service has been changed.");
    }

    @GetMapping("/show-all-services")
    public List<ServiceDTO> findAllService(){
        return serviceService.findAll();
    }

    @PostMapping("/add-service")
    public ResponseEntity<String> addService(@RequestBody @Valid ServiceDTO serviceDTO) {
        adminService.addService(serviceDTO);
        return ResponseEntity.ok().body("Service has been added successfully.");
    }

    @GetMapping("/show-all-sub-services")
    public List<SubServiceDTO> findAllSubServices(){
        return subServiceService.findAll();
    }

    @Transactional
    @PostMapping("/add-sub-service")
    public ResponseEntity<String> addSubService(@RequestBody @Valid SubServiceDTO subServiceDTO) {
        adminService.addSubService(subServiceDTO);
        return ResponseEntity.ok().body("Sub service has been added successfully.");
    }

    @GetMapping("/show-expert-by-email/{email}")
    public ExpertResponseDTO findExpertByEmail(@PathVariable String email) {
        return expertService.findExpertDTOByEmail(email);
    }



    @GetMapping("/filter-clients")
    public List<ClientDTO> filterClient(@RequestBody FilterClientDTO filterClientDTO) {
        return clientService.filterClient(filterClientDTO);
    }

    @GetMapping("/filter-experts")
    public List<ExpertResponseDTO> filterExpert(@RequestBody ExpertResponseDTO expertDTO) {
        return expertService.filterExpert(expertDTO);
    }

    @GetMapping("/filter-orders")
    public List<OrderDTO> filterOrder(@RequestBody FilterOrderDTO filterOrderDTO) {
        return adminService.filterOrder(filterOrderDTO);
    }

    @GetMapping("/count-client-orders/{id}")
    public int countClientOrders(@PathVariable Long id){
        return adminService.countClientOrders(id);
    }
}
