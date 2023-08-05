package org.example.converter;

import org.example.command.ClientSignUpCommand;
import org.example.entity.users.Client;
import org.example.security.PasswordHash;

import java.security.NoSuchAlgorithmException;

public class ClientSignUpCommandToClientConverter implements BaseConverter<ClientSignUpCommand, Client> {

    PasswordHash passwordHash = new PasswordHash();

    @Override
    public Client convert(ClientSignUpCommand clientSignUpCommand) throws NoSuchAlgorithmException {
        Client client = new Client();
        client.setFirstName(clientSignUpCommand.getFirstName());
        client.setLastName(clientSignUpCommand.getLastName());
        client.setEmail(clientSignUpCommand.getEmail());
        client.setPassword(passwordHash.createHashedPassword(clientSignUpCommand.getPassword()));
        client.setSignUpDate(clientSignUpCommand.getSignUpDate());
        client.setUserStatus(clientSignUpCommand.getUserStatus());
        return client;
    }

    @Override
    public ClientSignUpCommand convert(Client client) throws NoSuchAlgorithmException {
        ClientSignUpCommand clientSignUpCommand = new ClientSignUpCommand();
        clientSignUpCommand.setFirstName(client.getFirstName());
        clientSignUpCommand.setLastName(client.getLastName());
        clientSignUpCommand.setEmail(client.getEmail());
        clientSignUpCommand.setPassword(passwordHash.createHashedPassword(client.getPassword()));
        clientSignUpCommand.setSignUpDate(client.getSignUpDate());
        clientSignUpCommand.setUserStatus(client.getUserStatus());
        return clientSignUpCommand;
    }
}
