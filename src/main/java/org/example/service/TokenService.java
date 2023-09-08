package org.example.service;


import org.example.token.ConfirmationToken;

import java.util.Optional;

public interface TokenService {

    void saveToken(ConfirmationToken confirmationToken);

    Optional<ConfirmationToken> getToken(String token);
    void setConfirmedAt(String token);
}
