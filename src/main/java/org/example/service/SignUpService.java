package org.example.service;

import org.example.dto.ClientDTO;
import org.example.dto.ExpertDTO;

public interface SignUpService {
    String register(ClientDTO clientDTO);
    String register(ExpertDTO expertDTO);
    String confirmToken(String token);
    String buildEmail(String name, String link);
}
