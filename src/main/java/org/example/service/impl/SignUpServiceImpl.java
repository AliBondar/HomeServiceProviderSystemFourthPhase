package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.AdminDTO;
import org.example.dto.ClientDTO;
import org.example.dto.ExpertDTO;
import org.example.service.*;
import org.example.token.ConfirmationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final ClientService clientService;
    private final ExpertService expertService;
    private final AdminService adminService;
    private final TokenService tokenService;
    private final UserDetailService userDetailService;

    @Override
    @Transactional
    public String register(ClientDTO clientDTO) {
        String token = null;
        try {
            token = clientService.clientSignUp(clientDTO);
        } catch (jakarta.mail.SendFailedException e) {
            throw new RuntimeException(e);
        }
        return token;
    }

    @Override
    public String register(ExpertDTO expertDTO) {
        String token = null;
        try{
            token = expertService.expertSignUp(expertDTO);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return token;
    }

    @Override
    public String register(AdminDTO adminDTO) {
        String token = null;
        token = adminService.adminSignup(adminDTO);
        return token;
    }

    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = tokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        tokenService.setConfirmedAt(token);
        userDetailService.enableUser(
                confirmationToken.getUser().getEmail(), confirmationToken.getUser().getRole());
        return "User Signup Confirmed";
    }

}
