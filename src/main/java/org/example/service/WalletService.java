package org.example.service;


import org.example.dto.SubServiceDTO;
import org.example.dto.WalletDTO;
import org.example.entity.Wallet;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WalletService {


    Optional<WalletDTO> findAdminWalletByEmailAndPassword(String email, String password);

    Optional<WalletDTO> findClientWalletByEmailAndPassword(String email, String password);

    Optional<WalletDTO> findExpertWalletByEmailAndPassword(String email, String password);

    Optional<WalletDTO> findUserWallet();
}
