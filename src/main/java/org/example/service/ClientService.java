package org.example.service;



import org.example.command.ClientSignUpCommand;
import org.example.entity.users.Client;

import java.util.Optional;

public interface ClientService {
    Optional<Client> findClientByEmail(String email);

    Optional<Client> findClientByEmailAndPassword(String email, String password);

    void clientSignUp(ClientSignUpCommand clientSignUpCommand);

    void clientLogin(ClientSignUpCommand clientSignUpCommand);

    boolean isClientEmailDuplicated(String emailAddress);

    void editClientPassword(Long clientId, String password);


}
