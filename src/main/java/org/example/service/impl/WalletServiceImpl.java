package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.WalletDTO;
import org.example.entity.Wallet;
import org.example.entity.users.User;
import org.example.exception.NotFoundTheUserException;
import org.example.mapper.WalletMapper;
import org.example.repository.UserRepository;
import org.example.repository.WalletRepository;
import org.example.service.WalletService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final WalletMapper walletMapper = new WalletMapper();

    @Override
    public Optional<WalletDTO> findAdminWalletByEmailAndPassword(String email, String password) {
        Optional<Wallet> wallet = walletRepository.findAdminWalletByEmailAndPassword(email, password);
        if (wallet.isEmpty()){
            throw new NotFoundTheUserException("not found the user.");
        } else {
            return Optional.ofNullable(walletMapper.convert(wallet.get()));
        }
    }

    @Override
    public Optional<WalletDTO> findClientWalletByEmailAndPassword(String email, String password) {
        Optional<Wallet> wallet = walletRepository.findClientWalletByEmailAndPassword(email, password);
        if (wallet.isEmpty()){
            throw new NotFoundTheUserException("not found the user.");
        }else {
         return Optional.ofNullable(walletMapper.convert(wallet.get()));
        }
    }

    @Override
    public Optional<WalletDTO> findExpertWalletByEmailAndPassword(String email, String password) {
        Optional<Wallet> wallet = walletRepository.findExpertWalletByEmailAndPassword(email, password);
        if (wallet.isEmpty()){
            throw new NotFoundTheUserException("not found the user.");
        }else {
            return Optional.ofNullable(walletMapper.convert(wallet.get()));
        }
    }

    @Override
    public Optional<WalletDTO> findUserWallet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((User) authentication.getPrincipal()).getWallet().getId();
        Wallet wallet =  walletRepository.findById(id).get();
        return Optional.ofNullable(walletMapper.convert(wallet));
    }
}
