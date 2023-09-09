package org.example.service;


import org.example.dto.*;
import org.example.dto.response.ExpertResponseDTO;
import org.example.entity.Service;
import org.example.entity.users.Admin;
import org.example.entity.users.enums.UserStatus;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    void save(AdminDTO adminDTO);

    String adminSignup(AdminDTO adminDTO);

    Optional<Admin> findAdminByEmail(String email);

    Optional<Admin> findAdminByEmailAndPassword(String email, String password);

    void addExpertToSubService(Long expertId, Long subServiceId);

    void removeExpertFromSubService(Long expertId, Long subServiceId);

    void editExpertStatus(Long expertId, UserStatus userStatus);

    void editSubService(Long id, double newBasePrice, String newDescription);

    void addService(ServiceDTO serviceDTO);

    boolean isServiceDuplicated(String name);


    void addSubService(SubServiceDTO subServiceDTO);

    boolean isSubServiceDuplicated(String description, Service service);

    List<ClientDTO> filterClient(ClientDTO clientDTO);

    List<ExpertResponseDTO> filterExpert(ExpertResponseDTO expertDTO);

    List<OrderDTO> filterOrder(OrderDTO orderDTO);
}
