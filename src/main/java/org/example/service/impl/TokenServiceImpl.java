package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Token;
import org.example.repository.TokenRepository;
import org.example.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public void saveToken(Token confirmationToken) {
        tokenRepository.save(confirmationToken);
    }

    @Override
    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void setConfirmedAt(String token) {
        tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
