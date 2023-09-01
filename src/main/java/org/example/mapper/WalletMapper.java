package org.example.mapper;

import org.example.dto.WalletDTO;
import org.example.entity.Wallet;

import java.security.NoSuchAlgorithmException;

public class WalletMapper implements BaseMapper<WalletDTO , Wallet> {
    @Override
    public Wallet convert(WalletDTO walletDTO) {
        Wallet wallet = new Wallet();
        wallet.setBalance(walletDTO.getBalance());
        return wallet;
    }

    @Override
    public WalletDTO convert(Wallet wallet) {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setId(wallet.getId());
        walletDTO.setBalance(wallet.getBalance());
        return walletDTO;
    }
}
