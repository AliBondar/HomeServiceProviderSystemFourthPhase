package org.example.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.ClientDTO;
import org.example.dto.OrderDTO;
import org.example.entity.users.Expert;
import org.example.mapper.ClientMapper;
import org.example.entity.Offer;
import org.example.entity.Order;
import org.example.entity.Wallet;
import org.example.entity.enums.OrderStatus;
import org.example.entity.users.Client;
import org.example.entity.users.enums.UserStatus;
import org.example.exception.*;
import org.example.repository.*;
import org.example.security.PasswordHash;
import org.example.service.*;
import org.example.validation.Validation;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final WalletRepository walletRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final OfferRepository offerRepository;
    private final OfferService offerService;
    private final ExpertService expertService;
    private final ExpertRepository expertRepository;

    @PersistenceContext
    private EntityManager entityManager;
    private final ClientMapper clientMapper = new ClientMapper();


    @Override
    public void save(ClientDTO clientDTO) {
        Client client = clientMapper.convert(clientDTO);
        clientRepository.save(client);
    }

    @Override
    public void delete(ClientDTO clientDTO) {
        Client client = clientMapper.convert(clientDTO);
        clientRepository.delete(client);
    }

    @Override
    public ClientDTO findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(clientMapper::convert).orElse(null);
    }

    @Override
    public List<ClientDTO> findAll() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(clients)) return null;
        else {
            for (Client client : clients) {
                clientDTOList.add(clientMapper.convert(client));
            }
            return clientDTOList;
        }
    }

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
    public void clientSignUp(ClientDTO clientDTO) {
        Validation validation = new Validation();
        if (clientDTO.getFirstName() == null || clientDTO.getLastName() == null
                || clientDTO.getEmail() == null || clientDTO.getPassword() == null) {
            throw new EmptyFieldException("Field must be filled out.");
        } else if (validation.emailPatternMatches(clientDTO.getEmail())) {
            throw new InvalidEmailException("Email is invalid.");
        } else if (isClientEmailDuplicated(clientDTO.getEmail())) {
            throw new DuplicatedEmailException("Email already exists.");
        } else if (validation.passwordPatternMatches(clientDTO.getPassword())) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least eight characters, one special character, Capital digit and number");
        } else {
            clientDTO.setSignUpDate(LocalDate.now());
            clientDTO.setUserStatus(UserStatus.CLIENT);
            Wallet wallet = new Wallet();
            wallet.setBalance(0);
            walletRepository.save(wallet);
            clientDTO.setWallet(wallet);
            this.save(clientDTO);
        }
    }

    @Override
    public void clientLogin(ClientDTO clientDTO) {
        Validation validation = new Validation();
        ClientMapper clientmapper = new ClientMapper();
        if (clientDTO.getEmail() == null || clientDTO.getPassword() == null) {
            throw new EmptyFieldException("Field must filled out !");
        } else if (validation.emailPatternMatches(clientDTO.getEmail())) {
            throw new InvalidEmailException("Email is invalid.");
        } else if (isClientEmailDuplicated(clientDTO.getEmail())) {
            throw new DuplicatedEmailException("Email already exists.");
        } else if (validation.passwordPatternMatches(clientDTO.getPassword())) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least one special character, Capital digit and number");
        } else if (findClientByEmailAndPassword(clientDTO.getEmail(), clientDTO.getPassword()).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        } else {
            Client client = clientmapper.convert(clientDTO);
            System.out.println("Welcome " + client.getFirstName() + " " + client.getLastName());
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
    public void createOrder(OrderDTO orderDTO) {
        Validation validation = new Validation();
        if (orderDTO.getClientOfferedPrice() == 0 || orderDTO.getDescription() == null
                || orderDTO.getLocalTime() == null || orderDTO.getLocalDate() == null
                || orderDTO.getSubService() == null || orderDTO.getClientOfferedWorkDuration() == 0) {
            throw new EmptyFieldException("Fields must filled out.");
        } else if (!validation.isDateValid(orderDTO.getLocalDate())) {
            throw new InvalidDateException("Invalid date.");
        } else if (!validation.isTimeValid(orderDTO.getLocalTime())) {
            throw new InvalidTimeException("Invalid Time.");
        } else if (!validation.isOfferedPriceValid(orderDTO, orderDTO.getSubService())) {
            throw new InvalidPriceException("Invalid price.");
        } else {
            orderService.save(orderDTO);
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
        } else if (offerRepository.findAcceptedOfferByOrderId(orderId).get().getOfferedStartTime().after(Time.valueOf(LocalTime.now()))) {
            throw new InvalidTimeException("Invalid time.");
        } else {
            Order order = orderRepository.findById(orderId).get();
            order.setOrderStatus(OrderStatus.STARTED);
            orderRepository.save(order);
        }
    }

    @Override
    public void changeOrderStatusToDone(Long orderId) {
        if (orderRepository.findById(orderId).isEmpty()) {
            throw new NotFoundTheOrderException("not found the order.");
        } else if (orderRepository.findById(orderId).get().getOrderStatus() != OrderStatus.STARTED) {
            throw new InvalidTimeException("Invalid time.");
        } else {
            Order order = orderRepository.findById(orderId).get();
            Offer acceptedOffer = offerRepository.findAcceptedOfferByOrderId(orderId).get();
            Expert expert = acceptedOffer.getExpert();
            if (calculateDuration(acceptedOffer.getOfferedStartTime().toLocalTime()) > acceptedOffer.getExpertOfferedWorkDuration()) {
                expert.setScore(acceptedOffer.getExpert().getScore() - acceptedOffer.getExpertOfferedWorkDuration());
                if (expert.getScore() < 0) {
                    editExpertStatus(expert.getId(), UserStatus.DISABLED);
                }
                expertRepository.save(expert);
            }
            order.setOrderStatus(OrderStatus.DONE);
            orderRepository.save(order);
        }
    }

    @Override
    public void editExpertStatus(Long expertId, UserStatus userStatus) {
        if (expertRepository.findById(expertId).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        }else {
            Expert expert = expertRepository.findById(expertId).get();
            expert.setUserStatus(userStatus);
            expertRepository.save(expert);
        }
    }

    @Override
    public void changeOrderStatusToPaid(Long orderId) {
        if (orderRepository.findById(orderId).isEmpty()) {
            throw new NotFoundTheOrderException("not found the order.");
        } else if (orderRepository.findById(orderId).get().getOrderStatus() != OrderStatus.DONE) {
            throw new InvalidTimeException("Invalid time.");
        } else {
            Order order = orderRepository.findById(orderId).get();
            order.setOrderStatus(OrderStatus.PAID);
            orderRepository.save(order);
        }
    }

    @Override
    public int calculateDuration(LocalTime localTime) {
        Duration duration = Duration.between(localTime, LocalTime.now());
        return (int) duration.toHours();
    }

    @Override
    public List<ClientDTO> filterClient(ClientDTO clientDTO) {
        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> clientCriteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> clientRoot = clientCriteriaQuery.from(Client.class);
        createFilters(clientDTO, predicateList, criteriaBuilder, clientRoot);
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        clientCriteriaQuery.select(clientRoot).where(predicates);
        List<Client> resultList = entityManager.createQuery(clientCriteriaQuery).getResultList();
        List<ClientDTO> clientDTOList = new ArrayList<>();
        for (Client client : resultList) {
            clientDTOList.add(clientMapper.convert(client));
        }
        return clientDTOList;
    }

    @Override
    public void createFilters(ClientDTO clientDTO, List<Predicate> predicateList, CriteriaBuilder criteriaBuilder, Root<Client> clientRoot) {
        if (clientDTO.getFirstName() != null) {
            String firstName = "%" + clientDTO.getFirstName() + "%";
            predicateList.add(criteriaBuilder.like(clientRoot.get("firstName"), firstName));
        }
        if (clientDTO.getLastName() != null) {
            String lastName = "%" + clientDTO.getLastName() + "%";
            predicateList.add(criteriaBuilder.like(clientRoot.get("lastName"), lastName));
        }
        if (clientDTO.getEmail() != null) {
            String email = "%" + clientDTO.getEmail() + "%";
            predicateList.add(criteriaBuilder.like(clientRoot.get("email"), email));
        }
    }

    @Override
    public void payByWallet(Long orderId, Long clientId) {
        ClientDTO clientDTO = findById(clientId);
        OrderDTO orderDTO = orderService.findById(orderId);
        if (clientDTO == null) {
            throw new NotFoundTheUserException("not found the user.");
        } else if (orderDTO == null) {
            throw new NotFoundTheOrderException("not found the order.");
        } else if (orderDTO.getOrderStatus() != OrderStatus.DONE) {
            throw new OrderStatusException("order has not been done yet.");
        }
        Wallet wallet = clientDTO.getWallet();
        Offer offer = offerService.findAcceptedOfferByOrderId(orderId).get();
        if (offer.getOfferedPrice() > wallet.getBalance()) {
            throw new WalletBalanceException("balance is not enough");
        } else {
            Expert expert = offer.getExpert();
            updateClientWallet(clientId, clientDTO.getWallet().getBalance() + offer.getOfferedPrice());
            expertService.updateExpertWallet(expert.getId(),
                    expert.getWallet().getBalance() + offer.getOfferedPrice() * 0.7);
            changeOrderStatusToPaid(orderId);
        }
    }

    @Override
    public void updateClientWallet(Long clientId, double balance) {
        clientRepository.updateClientWallet(clientId, balance);
    }
}
