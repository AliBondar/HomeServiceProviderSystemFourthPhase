package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.command.ExpertSignUpCommand;
import org.example.converter.ExpertSIgnUpCommandToExpertConverter;
import org.example.entity.Wallet;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;
import org.example.exception.*;
import org.example.repository.ExpertRepository;
import org.example.repository.WalletRepository;
import org.example.security.PasswordHash;
import org.example.service.ExpertService;
import org.example.validation.Validation;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final WalletRepository walletRepository;

    @Override
    public Optional<Expert> findExpertByEmail(String email) {
        try {
            return expertRepository.findExpertByEmail(email);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Expert> findExpertByEmailAndPassword(String email, String password) {
        try {
            PasswordHash passwordHash = new PasswordHash();
            return expertRepository.findExpertByEmailAndPassword(email, passwordHash.createHashedPassword(password));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void expertSignUp(ExpertSignUpCommand expertSignUpCommand) throws IOException {
        Validation validation = new Validation();
        String [] path = expertSignUpCommand.getImageData().getPath().split("\\.");
        if (expertSignUpCommand.getFirstName() == null || expertSignUpCommand.getLastName() == null
                || expertSignUpCommand.getEmail() == null || expertSignUpCommand.getPassword() == null
                || expertSignUpCommand.getService() == null || expertSignUpCommand.getImageData() == null) {
            throw new EmptyFieldException("Field must be filled out.");
        } else if (validation.emailPatternMatches(expertSignUpCommand.getEmail())) {
            throw new InvalidEmailException("Email is invalid.");
        } else if (isExpertEmailDuplicated(expertSignUpCommand.getEmail())) {
            throw new DuplicatedEmailException("Email already exists.");
        } else if (validation.passwordPatternMatches(expertSignUpCommand.getPassword())) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least eight characters, one special character, Capital digit and number");
        } else if (!path[path.length - 1].equalsIgnoreCase("JPG")) {
            throw new ImageFormatException("Image format must be jpg");
        } else if (Files.size(Paths.get(expertSignUpCommand.getImageData().getPath())) > 300000) {
            throw new ImageSizeException("Image size must be less than 300kb");
        } else {
            expertSignUpCommand.setSignUpDate(LocalDate.now());
            expertSignUpCommand.setUserStatus(UserStatus.NEW);
            expertSignUpCommand.setScore(0);
            ExpertSIgnUpCommandToExpertConverter expertSIgnUpCommandToExpertConverter = new ExpertSIgnUpCommandToExpertConverter();
            Expert expert = null;
            try {
                expert = expertSIgnUpCommandToExpertConverter.convert(expertSignUpCommand);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            Wallet wallet = new Wallet();
            wallet.setBalance(0);
            walletRepository.save(wallet);
            expert.setWallet(wallet);
            expertRepository.save(expert);
        }
    }

    @Override
    public boolean isExpertEmailDuplicated(String email) {
        return this.findExpertByEmail(email).isPresent();
    }

    public List<Expert> findExpertsByUserStatus(UserStatus userStatus) {
        if (expertRepository.findExpertsByUserStatus(userStatus).size() == 0) {
            throw new NotFoundTheUserException("No user with this status !");
        } else {
            return expertRepository.findExpertsByUserStatus(userStatus);
        }
    }

    @Override
    public void editExpertPassword(Long expertId, String password) {
        Validation validation = new Validation();
        PasswordHash passwordHash = new PasswordHash();
        if (expertRepository.findById(expertId).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        } else if (validation.passwordPatternMatches(password)) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least one special character, Capital digit and number");
        } else {
            try {
                Expert expert = expertRepository.findById(expertId).get();
                expert.setPassword(passwordHash.createHashedPassword(password));
                expertRepository.save(expert);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
