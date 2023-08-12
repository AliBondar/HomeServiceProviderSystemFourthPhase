package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.ExpertDTO;
import org.example.dto.OfferDTO;
import org.example.mapper.ExpertMapper;
import org.example.mapper.OfferMapper;
import org.example.entity.Offer;
import org.example.entity.Order;
import org.example.entity.Wallet;
import org.example.entity.enums.OrderStatus;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;
import org.example.exception.*;
import org.example.repository.ExpertRepository;
import org.example.repository.OfferRepository;
import org.example.repository.OrderRepository;
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
    private final OfferRepository offerRepository;
    private final OrderRepository orderRepository;

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
    public void expertSignUp(ExpertDTO expertDTO) throws IOException {
        Validation validation = new Validation();
        if (expertDTO.getFirstName() == null || expertDTO.getLastName() == null
                || expertDTO.getEmail() == null || expertDTO.getPassword() == null
                || expertDTO.getService() == null || expertDTO.getImageData() == null) {
            throw new EmptyFieldException("Field must be filled out.");
        }
        String [] path = expertDTO.getImageData().getPath().split("\\.");
        if (validation.emailPatternMatches(expertDTO.getEmail())) {
            throw new InvalidEmailException("Email is invalid.");
        } else if (isExpertEmailDuplicated(expertDTO.getEmail())) {
            throw new DuplicatedEmailException("Email already exists.");
        } else if (validation.passwordPatternMatches(expertDTO.getPassword())) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least eight characters, one special character, Capital digit and number");
        } else if (!path[path.length - 1].equalsIgnoreCase("JPG")) {
            throw new ImageFormatException("Image format must be jpg");
        } else if (Files.size(Paths.get(expertDTO.getImageData().getPath())) > 300000) {
            throw new ImageSizeException("Image size must be less than 300kb");
        } else {
            expertDTO.setSignUpDate(LocalDate.now());
            expertDTO.setUserStatus(UserStatus.NEW);
            expertDTO.setScore(0);
            ExpertMapper expertMapper = new ExpertMapper();
            Expert expert = null;
            try {
                expert = expertMapper.convert(expertDTO);
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

    @Override
    public void createOffer(OfferDTO offerDTO) {
        Validation validation = new Validation();
        OfferMapper offerMapper = new OfferMapper();
        if (offerDTO.getExpert() == null || offerDTO.getOfferedPrice() == 0
                || offerDTO.getExpertOfferedWorkDuration() == 0 || offerDTO.getOrder() == null
                || offerDTO.getOfferedStartTime() == null || offerDTO.getOfferedStartDate() == null) {
            throw new EmptyFieldException("Fields must filled out.");
        } else if (offerDTO.getExpert().getUserStatus() != UserStatus.CONFIRMED) {
            throw new UserConfirmationException("User is not confirmed yet.");
        } else if (!validation.isOfferedPriceValid(offerDTO, offerDTO.getOrder().getSubService())) {
            throw new InvalidPriceException("Price is not valid.");
        } else if (!validation.isTimeValid(offerDTO.getOfferedStartTime())) {
            throw new InvalidTimeException("Time is invalid.");
        } else if (!validation.isDateValid(offerDTO.getOfferedStartDate())) {
            throw new InvalidDateException("Date is invalid.");
        }else {
            try {
                Offer offer = offerMapper.convert(offerDTO);
                offerRepository.save(offer);
                Order order = orderRepository.findById(offerDTO.getOrder().getId()).get();
                order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_CHOOSE);
                orderRepository.save(order);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
