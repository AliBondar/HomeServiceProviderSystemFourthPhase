package org.example.mapper;

import org.example.dto.ClientDTO;
import org.example.entity.users.Client;
import org.example.security.PasswordHash;

import java.security.NoSuchAlgorithmException;

public class ClientMapper implements BaseMapper<ClientDTO, Client> {

    PasswordHash passwordHash = new PasswordHash();

    @Override
    public Client convert(ClientDTO clientDTO) throws NoSuchAlgorithmException {
        Client client = new Client();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setPassword(passwordHash.createHashedPassword(clientDTO.getPassword()));
        client.setSignUpDate(clientDTO.getSignUpDate());
        client.setUserStatus(clientDTO.getUserStatus());
        return client;
    }

    @Override
    public ClientDTO convert(Client client) throws NoSuchAlgorithmException {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName(client.getFirstName());
        clientDTO.setLastName(client.getLastName());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPassword(passwordHash.createHashedPassword(client.getPassword()));
        clientDTO.setSignUpDate(client.getSignUpDate());
        clientDTO.setUserStatus(client.getUserStatus());
        return clientDTO;
    }
}
