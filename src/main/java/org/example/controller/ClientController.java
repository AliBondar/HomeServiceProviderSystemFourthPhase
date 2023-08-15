package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ClientDTO;
import org.example.entity.users.Client;
import org.example.repository.ClientRepository;
import org.example.service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    @PostMapping("/signup")
    @ResponseBody
    public void signUp(@RequestBody ClientDTO clientDTO) {
        clientService.clientSignUp(clientDTO);
    }

    @PostMapping("/test")
    @ResponseBody
    public void signUp(@RequestBody String str) {
        Client client = new Client();
        client.setFirstName(str);
        clientRepository.save(client);
    }


}
