package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.dto.response.ExpertResponseDTO;
import org.example.entity.SubService;
import org.example.entity.Wallet;
import org.example.entity.users.Admin;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;
import org.example.exception.*;
import org.example.mapper.AdminMapper;
import org.example.repository.AdminRepository;
import org.example.repository.ExpertRepository;
import org.example.repository.SubServiceRepository;
import org.example.repository.WalletRepository;
import org.example.security.PasswordHash;
import org.example.service.*;
import org.example.token.ConfirmationToken;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ExpertRepository expertRepository;
    private final SubServiceRepository subServiceRepository;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;
    private final ClientService clientService;
    private final ExpertService expertService;
    private final WalletRepository walletRepository;
    private final OrderService orderService;
    private final TokenService tokenService;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final AdminMapper adminMapper = new AdminMapper();
    PasswordHash passwordHash = new PasswordHash();

    @Override
    public void save(AdminDTO adminDTO) {
        adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        Admin admin = adminMapper.convert(adminDTO);
        adminRepository.save(admin);
    }

    @Override
    public String adminSignup(AdminDTO adminDTO) {
        adminDTO.setSignUpDate(LocalDate.now());
        adminDTO.setUserStatus(UserStatus.ADMIN);
        Wallet wallet = new Wallet();
        wallet.setBalance(0);
        walletRepository.save(wallet);
        adminDTO.setWallet(wallet);
        save(adminDTO);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15),
                adminRepository.findByEmail(adminDTO.getEmail()).get());
        tokenService.saveToken(confirmationToken);

        SimpleMailMessage mailMessage = emailSenderService.createEmail(
                adminDTO.getEmail(), confirmationToken.getToken(), "admin");
        emailSenderService.sendEmail(mailMessage);

        return token;
    }

    @Override
    public Optional<Admin> findAdminByEmail(String email) {
        try {
            return adminRepository.findByEmail(email);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Admin> findAdminByEmailAndPassword(String email, String password) {
        try {
            return adminRepository.findByEmailAndPassword(email, passwordHash.createHashedPassword(password));
        } catch (Exception e) {
            return Optional.empty();
        }
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
        } else {
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
        } else {
            Expert expert = expertRepository.findById(expertId).get();
            expert.setUserStatus(userStatus);
            expertRepository.save(expert);
        }
    }

    @Override
    public void editSubService(Long id, double newBasePrice, String newDescription) {
        if (subServiceRepository.findById(id).isEmpty()) {
            throw new NotFoundTheSubServiceException("Couldn't find the sub service !");
        } else {
            SubService subService = subServiceRepository.findById(id).get();
            subService.setBasePrice(newBasePrice);
            subService.setDescription(newDescription);
            subServiceRepository.save(subService);
        }
    }

    @Override
    public void addService(ServiceDTO serviceDTO) {
        if (isServiceDuplicated(serviceDTO.getName())) {
            throw new DuplicatedServiceException("Service already exists !");
        } else {
            serviceService.save(serviceDTO);
        }
    }

    @Override
    public boolean isServiceDuplicated(String name) {
        return serviceService.findServiceByName(name).isPresent();
    }

    @Override
    public void addSubService(SubServiceDTO subServiceDTO) {
        if (isSubServiceDuplicated(subServiceDTO.getDescription(), subServiceDTO.getService())) {
            throw new DuplicatedSubServiceException("Sub service already exists !");
        } else if (serviceService.findServiceByName(subServiceDTO.getService().getName()).isEmpty()) {
            throw new NotFoundTheServiceException("Couldn't find the service !");
        } else {
            subServiceService.save(subServiceDTO);
        }
    }

    @Override
    public boolean isSubServiceDuplicated(String description, org.example.entity.Service service) {
        return subServiceService.findByDescriptionAndService(description, service).isPresent();
    }

    @Override
    public List<ClientDTO> filterClient(ClientDTO clientDTO) {
        return clientService.filterClient(clientDTO);
    }

    @Override
    public List<ExpertResponseDTO> filterExpert(ExpertResponseDTO expertDTO) {
        return expertService.filterExpert(expertDTO);
    }

    @Override
    public List<OrderDTO> filterOrder(OrderDTO orderDTO) {
        return orderService.filterOrder(orderDTO);
    }

    @Override
    public int countClientOrders(Long id){
        return orderService.countClientOrders(id);
    }
}
