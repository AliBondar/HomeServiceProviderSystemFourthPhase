package org.example.service;

import org.example.entity.Token;

import java.util.Optional;

public interface TokenService {

    void saveToken(Token confirmationToken);

    Optional<Token> getToken(String token);
    void setConfirmedAt(String token);
}
