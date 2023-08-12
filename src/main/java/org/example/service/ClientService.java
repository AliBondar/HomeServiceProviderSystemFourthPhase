package org.example.service;



import org.example.dto.ClientDTO;
import org.example.dto.OrderDTO;
import org.example.entity.Offer;
import org.example.entity.users.Client;

import java.util.Optional;

public interface ClientService {
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
}
