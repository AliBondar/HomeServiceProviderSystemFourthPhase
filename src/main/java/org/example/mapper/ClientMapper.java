package org.example.mapper;

import org.example.dto.ClientDTO;
import org.example.entity.users.Client;
import org.example.entity.users.enums.Role;
import org.example.security.PasswordHash;

import java.security.NoSuchAlgorithmException;

public class ClientMapper implements BaseMapper<ClientDTO, Client> {

    PasswordHash passwordHash = new PasswordHash();

    @Override
    public Client convert(ClientDTO clientDTO) {
        Client client = new Client();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setRole(Role.CLIENT);
        try {
            client.setPassword(passwordHash.createHashedPassword(clientDTO.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        client.setSignUpDate(clientDTO.getSignUpDate());
        client.setUserStatus(clientDTO.getUserStatus());
        client.setWallet(clientDTO.getWallet());
        return client;
    }

    @Override
    public ClientDTO convert(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName(client.getFirstName());
        clientDTO.setLastName(client.getLastName());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setId(client.getId());
        try {
            clientDTO.setPassword(passwordHash.createHashedPassword(client.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        clientDTO.setSignUpDate(client.getSignUpDate());
        clientDTO.setUserStatus(client.getUserStatus());
        clientDTO.setWallet(client.getWallet());
        return clientDTO;
    }
}
