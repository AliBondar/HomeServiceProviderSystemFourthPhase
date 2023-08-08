package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.command.ClientSignUpCommand;
import org.example.converter.ClientSignUpCommandToClientConverter;
import org.example.entity.Wallet;
import org.example.entity.users.Client;
import org.example.entity.users.enums.UserStatus;
import org.example.exception.*;
import org.example.repository.ClientRepository;
import org.example.repository.WalletRepository;
import org.example.security.PasswordHash;
import org.example.service.ClientService;
import org.example.service.WalletService;
import org.example.validation.Validation;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final WalletRepository walletRepository;

    @Override
    public Optional<Client> findClientByEmail(String email) {
        try {
            return clientRepository.findClientByEmail(email);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Client> findClientByEmailAndPassword(String email, String password) {
        try {
            PasswordHash passwordHash = new PasswordHash();
            return clientRepository.findClientByEmailAndPassword(email, passwordHash.createHashedPassword(password));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public void clientSignUp(ClientSignUpCommand clientSignUpCommand) {
        Validation validation = new Validation();
        if (clientSignUpCommand.getFirstName() == null || clientSignUpCommand.getLastName() == null
                || clientSignUpCommand.getEmail() == null || clientSignUpCommand.getPassword() == null) {
            throw new EmptyFieldException("Field must be filled out.");
        } else if (validation.emailPatternMatches(clientSignUpCommand.getEmail())) {
            throw new InvalidEmailException("Email is invalid.");
        } else if (isClientEmailDuplicated(clientSignUpCommand.getEmail())) {
            throw new DuplicatedEmailException("Email already exists.");
        } else if (validation.passwordPatternMatches(clientSignUpCommand.getPassword())) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least eight characters, one special character, Capital digit and number");
        } else {
            clientSignUpCommand.setSignUpDate(LocalDate.now());
            clientSignUpCommand.setUserStatus(UserStatus.CLIENT);
            ClientSignUpCommandToClientConverter clientSignUpCommandToClientConverter = new ClientSignUpCommandToClientConverter();
            Client client = null;
            try {
                client = clientSignUpCommandToClientConverter.convert(clientSignUpCommand);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            Wallet wallet = new Wallet();
            wallet.setBalance(0);
            walletRepository.save(wallet);
            client.setWallet(wallet);
            clientRepository.save(client);
        }
    }

    @Override
    public void clientLogin(ClientSignUpCommand clientSignUpCommand) {
        Validation validation = new Validation();
        PasswordHash passwordHash = new PasswordHash();
        ClientSignUpCommandToClientConverter clientSignUpCommandToClientConverter = new ClientSignUpCommandToClientConverter();
        if (clientSignUpCommand.getEmail() == null || clientSignUpCommand.getPassword() == null) {
            throw new EmptyFieldException("Field must filled out !");
        } else if (validation.emailPatternMatches(clientSignUpCommand.getEmail())) {
            throw new InvalidEmailException("Email is invalid.");
        } else if (isClientEmailDuplicated(clientSignUpCommand.getEmail())) {
            throw new DuplicatedEmailException("Email already exists.");
        } else if (validation.passwordPatternMatches(clientSignUpCommand.getPassword())) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least one special character, Capital digit and number");
        } else if (findClientByEmailAndPassword(clientSignUpCommand.getEmail(), clientSignUpCommand.getPassword()).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        } else {
            try {
                Client client = clientSignUpCommandToClientConverter.convert(clientSignUpCommand);
                System.out.println("Welcome " + client.getFirstName() + " " + client.getLastName());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isClientEmailDuplicated(String emailAddress) {
        return this.findClientByEmail(emailAddress).isPresent();
    }

    @Override
    public void editClientPassword(Long clientId, String password) {
        Validation validation = new Validation();
        PasswordHash passwordHash = new PasswordHash();
        if (clientRepository.findById(clientId).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        } else if (validation.passwordPatternMatches(password)) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least one special character, Capital digit and number");
        } else {
            try {
                Client client = clientRepository.findById(clientId).get();
                client.setPassword(passwordHash.createHashedPassword(password));
                clientRepository.save(client);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
