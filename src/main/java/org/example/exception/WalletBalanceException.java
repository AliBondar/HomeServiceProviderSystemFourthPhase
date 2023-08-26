package org.example.exception;

public class WalletBalanceException extends RuntimeException {
    public WalletBalanceException(String message){
        super(message);
    }
}
