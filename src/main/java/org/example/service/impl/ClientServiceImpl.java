package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.command.ClientSignUpCommand;
import org.example.command.OrderCommand;
import org.example.converter.ClientSignUpCommandToClientConverter;
import org.example.converter.OrderCommandToOrderConverter;
import org.example.entity.Offer;
import org.example.entity.Order;
import org.example.entity.Wallet;
import org.example.entity.enums.OrderStatus;
import org.example.entity.users.Client;
import org.example.entity.users.enums.UserStatus;
import org.example.exception.*;
import org.example.repository.ClientRepository;
import org.example.repository.OfferRepository;
import org.example.repository.OrderRepository;
import org.example.repository.WalletRepository;
import org.example.security.PasswordHash;
import org.example.service.ClientService;
import org.example.service.WalletService;
import org.example.validation.Validation;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final WalletRepository walletRepository;
    private final OrderRepository orderRepository;
    private final OfferRepository offerRepository;

    @Override
    public Optional<Client> findClientByEmail(String email) {
        try {
            return clientRepository.findClientByEmail(email);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Client> findClientByEmailAndPassword(String email, String password) {
        try {
            PasswordHash passwordHash = new PasswordHash();
            return clientRepository.findClientByEmailAndPassword(email, passwordHash.createHashedPassword(password));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public void clientSignUp(ClientSignUpCommand clientSignUpCommand) {
        Validation validation = new Validation();
        if (clientSignUpCommand.getFirstName() == null || clientSignUpCommand.getLastName() == null
                || clientSignUpCommand.getEmail() == null || clientSignUpCommand.getPassword() == null) {
            throw new EmptyFieldException("Field must be filled out.");
        } else if (validation.emailPatternMatches(clientSignUpCommand.getEmail())) {
            throw new InvalidEmailException("Email is invalid.");
        } else if (isClientEmailDuplicated(clientSignUpCommand.getEmail())) {
            throw new DuplicatedEmailException("Email already exists.");
        } else if (validation.passwordPatternMatches(clientSignUpCommand.getPassword())) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least eight characters, one special character, Capital digit and number");
        } else {
            clientSignUpCommand.setSignUpDate(LocalDate.now());
            clientSignUpCommand.setUserStatus(UserStatus.CLIENT);
            ClientSignUpCommandToClientConverter clientSignUpCommandToClientConverter = new ClientSignUpCommandToClientConverter();
            Client client = null;
            try {
                client = clientSignUpCommandToClientConverter.convert(clientSignUpCommand);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            Wallet wallet = new Wallet();
            wallet.setBalance(0);
            walletRepository.save(wallet);
            client.setWallet(wallet);
            clientRepository.save(client);
        }
    }

    @Override
    public void clientLogin(ClientSignUpCommand clientSignUpCommand) {
        Validation validation = new Validation();
        PasswordHash passwordHash = new PasswordHash();
        ClientSignUpCommandToClientConverter clientSignUpCommandToClientConverter = new ClientSignUpCommandToClientConverter();
        if (clientSignUpCommand.getEmail() == null || clientSignUpCommand.getPassword() == null) {
            throw new EmptyFieldException("Field must filled out !");
        } else if (validation.emailPatternMatches(clientSignUpCommand.getEmail())) {
            throw new InvalidEmailException("Email is invalid.");
        } else if (isClientEmailDuplicated(clientSignUpCommand.getEmail())) {
            throw new DuplicatedEmailException("Email already exists.");
        } else if (validation.passwordPatternMatches(clientSignUpCommand.getPassword())) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least one special character, Capital digit and number");
        } else if (findClientByEmailAndPassword(clientSignUpCommand.getEmail(), clientSignUpCommand.getPassword()).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        } else {
            try {
                Client client = clientSignUpCommandToClientConverter.convert(clientSignUpCommand);
                System.out.println("Welcome " + client.getFirstName() + " " + client.getLastName());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isClientEmailDuplicated(String emailAddress) {
        return this.findClientByEmail(emailAddress).isPresent();
    }

    @Override
    public void editClientPassword(Long clientId, String password) {
        Validation validation = new Validation();
        PasswordHash passwordHash = new PasswordHash();
        if (clientRepository.findById(clientId).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        } else if (validation.passwordPatternMatches(password)) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least one special character, Capital digit and number");
        } else {
            try {
                Client client = clientRepository.findById(clientId).get();
                client.setPassword(passwordHash.createHashedPassword(password));
                clientRepository.save(client);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void createOrder(OrderCommand orderCommand) {
        Validation validation = new Validation();
        OrderCommandToOrderConverter orderCommandToOrderConverter = new OrderCommandToOrderConverter();
        if (orderCommand.getClientOfferedPrice() == 0 || orderCommand.getDescription() == null
                || orderCommand.getLocalTime() == null || orderCommand.getLocalDate() == null
                || orderCommand.getSubService() == null || orderCommand.getClientOfferedWorkDuration() == 0) {
            throw new EmptyFieldException("Fields must filled out.");
        } else if (!validation.isDateValid(orderCommand.getLocalDate())) {
            throw new InvalidDateException("Invalid date.");
        } else if (!validation.isTimeValid(orderCommand.getLocalTime())) {
            throw new InvalidTimeException("Invalid Time.");
        } else if (!validation.isOfferedPriceValid(orderCommand, orderCommand.getSubService())) {
            throw new InvalidPriceException("Invalid price.");
        } else {
            try {
                Order order = orderCommandToOrderConverter.convert(orderCommand);
                orderRepository.save(order);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void acceptOffer(Offer offer) {
        offer.setAccepted(true);
        Order order = offer.getOrder();
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_ARRIVES);
        order.setExpert(offer.getExpert());
        orderRepository.save(order);
        offerRepository.save(offer);
    }

    @Override
    public void changeOrderStatusToStarted(Long orderId) {
        if (orderRepository.findById(orderId).isEmpty()) {
            throw new NotFoundTheOrderException("Couldn't find the order.");
        } else if (offerRepository.findAcceptedOfferByOrderId(orderId).isEmpty()) {
            throw new NotFoundTheOfferException("Couldn't find the offer.");
        } else if (offerRepository.findAcceptedOfferByOrderId(orderId).get().getOfferedStartDate().isAfter(LocalDate.now())) {
            throw new InvalidDateException("Invalid date.");
        } else if (offerRepository.findAcceptedOfferByOrderId(orderId).get().getOfferedStartTime().after(Time.valueOf(LocalTime.now()))){
            throw new InvalidTimeException("Invalid time.");
        } else {
            Order order = orderRepository.findById(orderId).get();
            order.setOrderStatus(OrderStatus.STARTED);
            orderRepository.save(order);
        }
    }
}
