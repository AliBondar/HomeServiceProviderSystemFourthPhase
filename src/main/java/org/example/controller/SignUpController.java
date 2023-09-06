package org.example.controller;

import jakarta.mail.SendFailedException;
import lombok.AllArgsConstructor;
import org.example.dto.ClientDTO;
import org.example.service.ClientService;
import org.example.service.SignUpService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/signup")
@AllArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;


    @PostMapping("/client-signup")
    public String signup(@RequestBody ClientDTO clientDTO){
        System.out.println("Controller+++++++++++++++++++++++++++++++++++++++");
            return signUpService.register(clientDTO);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return signUpService.confirmToken(token);
    }

}
