package org.example.service;



import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.dto.ClientDTO;
import org.example.dto.OrderDTO;
import org.example.dto.ScoreDTO;
import org.example.entity.Offer;
import org.example.entity.users.Client;
import org.example.entity.users.enums.UserStatus;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    void save(ClientDTO clientDTO);

    void delete(ClientDTO clientDTO);

    ClientDTO findById(Long id);

    List<ClientDTO> findAll();

    Optional<Client> findClientByEmail(String email);

    Optional<Client> findClientByEmailAndPassword(String email, String password);

    void clientSignUp(ClientDTO clientDTO);

    void clientLogin(ClientDTO clientDTO);

    boolean isClientEmailDuplicated(String emailAddress);

    void editClientPassword(Long clientId, String password);

    void createOrder(OrderDTO orderDTO);

    void acceptOffer(Offer offer);

    void changeOrderStatusToStarted(Long orderId);

    void changeOrderStatusToDone(Long orderId);

    void editExpertStatus(Long expertId, UserStatus userStatus);

    void submitScore(ScoreDTO scoreDTO);

    void changeOrderStatusToPaid(Long orderId);

    int calculateDuration(LocalTime localTime);

    List<ClientDTO> filterClient(ClientDTO clientDTO);

    void createFilters(ClientDTO clientDTO, List<Predicate> predicateList, CriteriaBuilder criteriaBuilder, Root<Client> customerRoot);

    void payByWallet(Long orderId, Long clientId);

    void updateClientWallet(Long clientId, double balance);
}
