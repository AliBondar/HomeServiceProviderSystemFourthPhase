package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.ClientDTO;
import org.example.service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/signup")
@AllArgsConstructor
public class SignUpController {

    private final ClientService clientService;

    @PostMapping("/client-signup")
    public void signup(@RequestBody ClientDTO clientDTO){
        clientService.clientSignUp(clientDTO);
    }

}
