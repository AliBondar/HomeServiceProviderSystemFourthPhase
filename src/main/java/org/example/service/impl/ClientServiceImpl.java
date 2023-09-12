package org.example.service.impl;

import jakarta.mail.SendFailedException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.example.dto.CardDTO;
import org.example.dto.ClientDTO;
import org.example.dto.OrderDTO;
import org.example.dto.ScoreDTO;
import org.example.dto.request.FilterClientDTO;
import org.example.entity.users.Expert;
import org.example.entity.users.User;
import org.example.entity.users.enums.Role;
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
import org.example.token.ConfirmationToken;
import org.example.token.ConfirmationTokenService;
import org.example.validation.Validation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final WalletRepository walletRepository;
    private final OrderRepository orderRepository;
    private final SubServiceRepository subServiceRepository;
    private final OrderService orderService;
    private final OfferRepository offerRepository;
    private final OfferService offerService;
    private final ExpertService expertService;
    private final ExpertRepository expertRepository;
    private final ScoreService scoreService;
    private final TokenService tokenService;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordEncoder passwordEncoder;

    private final Validation validation = new Validation();

    @PersistenceContext
    private EntityManager entityManager;
    private final ClientMapper clientMapper = new ClientMapper();


    @Override
    public void save(ClientDTO clientDTO) {
        clientDTO.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        Client client = clientMapper.convert(clientDTO);
        clientRepository.save(client);
    }

    public void saveClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setRole(Role.CLIENT);
        client.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        client.setSignUpDate(clientDTO.getSignUpDate());
        client.setUserStatus(clientDTO.getUserStatus());
        client.setWallet(clientDTO.getWallet());
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
    public String clientSignUp(ClientDTO clientDTO) throws SendFailedException {
        Validation validation = new Validation();

        if (clientDTO.getFirstName() == null || clientDTO.getLastName() == null
                || clientDTO.getEmail() == null || clientDTO.getPassword() == null) {
            throw new EmptyFieldException("Field must be filled out.");
        }
        else if (validation.emailPatternMatches(clientDTO.getEmail())) {
            throw new InvalidEmailException("Email is invalid.");
        }
        else if (isClientEmailDuplicated(clientDTO.getEmail())) {
            throw new DuplicatedEmailException("Email already exists.");
        }
        else if (validation.passwordPatternMatches(clientDTO.getPassword())) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least eight characters, one special character, Capital digit and number");
        }
        else {

            clientDTO.setSignUpDate(LocalDate.now());
            clientDTO.setUserStatus(UserStatus.CLIENT);
            Wallet wallet = new Wallet();
            wallet.setBalance(0);
            walletRepository.save(wallet);
            clientDTO.setWallet(wallet);
            this.save(clientDTO);

            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15),
                    clientRepository.findClientByEmail(clientDTO.getEmail()).get());
            tokenService.saveToken(confirmationToken);

            SimpleMailMessage mailMessage = emailSenderService.createEmail(
                    clientDTO.getEmail(), confirmationToken.getToken(), "client");
            emailSenderService.sendEmail(mailMessage);

            return token;
        }

    }

    @Override
    public void clientLogin(ClientDTO clientDTO) {

        Validation validation = new Validation();
        ClientMapper clientmapper = new ClientMapper();

        if (clientDTO.getEmail() == null || clientDTO.getPassword() == null) {
            throw new EmptyFieldException("Field must filled out !");
        }
        else if (validation.emailPatternMatches(clientDTO.getEmail())) {
            throw new InvalidEmailException("Email is invalid.");
        }
        else if (isClientEmailDuplicated(clientDTO.getEmail())) {
            throw new DuplicatedEmailException("Email already exists.");
        }
        else if (validation.passwordPatternMatches(clientDTO.getPassword())) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least one special character, Capital digit and number");
        }
        else if (findClientByEmailAndPassword(clientDTO.getEmail(), clientDTO.getPassword()).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        }
        else {
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

        if (clientRepository.findById(clientId).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        }
        else if (validation.passwordPatternMatches(password)) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least one special character, Capital digit and number");
        }
        else {
                Client client = clientRepository.findById(clientId).get();
                client.setPassword(passwordEncoder.encode(password));
                clientRepository.save(client);
        }

    }

    @Override
    public void editClientPassword(String password){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long clientId = ((User) authentication.getPrincipal()).getId();

        if (clientRepository.findById(clientId).isEmpty()) {
            throw new NotFoundTheUserException("Couldn't find the user !");
        }
        else if (validation.passwordPatternMatches(password)) {
            throw new InvalidPasswordException("Password is invalid. It must contain at least one special character, Capital digit and number");
        }
        else {
            Client client = clientRepository.findById(clientId).get();
            client.setPassword(passwordEncoder.encode(password));
            clientRepository.save(client);
        }

    }

    @Override
    public void createOrder(OrderDTO orderDTO) {

        Validation validation = new Validation();

        if (orderDTO.getClientOfferedPrice() == 0 || orderDTO.getDescription() == null
                || orderDTO.getLocalTime() == null || orderDTO.getLocalDate() == null
                || orderDTO.getSubServiceId() == null || orderDTO.getClientOfferedWorkDuration() == 0) {
            throw new EmptyFieldException("Fields must filled out.");
        }
        else if (!validation.isDateValid(orderDTO.getLocalDate())) {
            throw new InvalidDateException("Invalid date.");
        }
        else if (!validation.isTimeValid(orderDTO.getLocalTime())) {
            throw new InvalidTimeException("Invalid Time.");
        }
        else if (!validation.isOfferedPriceValid(orderDTO, subServiceRepository.findById(orderDTO.getSubServiceId()).get())) {
            throw new InvalidPriceException("Invalid price.");
        }
        else {
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
    public void acceptOffer(Long id) {

        Offer offer = offerRepository.findById(id).get();
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
        }
        else if (offerRepository.findAcceptedOfferByOrderId(orderId).isEmpty()) {
            throw new NotFoundTheOfferException("Couldn't find the offer.");
        }
        else if (offerRepository.findAcceptedOfferByOrderId(orderId).get().getOfferedStartDate().isAfter(LocalDate.now())) {
            throw new InvalidDateException("Invalid date.");
        }
        else if (offerRepository.findAcceptedOfferByOrderId(orderId).get().getOfferedStartTime().after(Time.valueOf(LocalTime.now()))) {
            throw new InvalidTimeException("Invalid time.");
        }
        else {
            Order order = orderRepository.findById(orderId).get();
            order.setOrderStatus(OrderStatus.STARTED);
            orderRepository.save(order);
        }

    }

    @Override
    public void changeOrderStatusToDone(Long orderId) {

        if (orderRepository.findById(orderId).isEmpty()) {
            throw new NotFoundTheOrderException("not found the order.");
        }
        else if (orderRepository.findById(orderId).get().getOrderStatus() != OrderStatus.STARTED) {
            throw new InvalidTimeException("Invalid time.");
        }
        else {
            Order order = orderRepository.findById(orderId).get();
            Offer acceptedOffer = offerRepository.findAcceptedOfferByOrderId(orderId).get();
            Expert expert = acceptedOffer.getExpert();
            int delay = calculateDuration(acceptedOffer.getOfferedStartTime().toLocalTime());
            if (delay > acceptedOffer.getExpertOfferedWorkDuration()) {
                expert.setScore(acceptedOffer.getExpert().getScore() - delay);
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
        } else {
            Expert expert = expertRepository.findById(expertId).get();
            expert.setUserStatus(userStatus);
            expertRepository.save(expert);
        }

    }

    @Override
    public void createScore(ScoreDTO scoreDTO) {
        Order order = scoreDTO.getOrder();
        if (order == null) {
            throw new EmptyFieldException("not found the order.");
        } else if (order.getOrderStatus() != OrderStatus.DONE) {
            throw new OrderStatusException("order has not get done yet.");
        } else if (order.getScore() != null) {
            throw new DuplicatedScoreException("order already has score.");
        } else if (!validation.isScoreValid(scoreDTO.getScore())) {
            throw new ScoreRangeException("score is not valid. It must be between 1 and 5");
        } else {
            expertRepository.updateExpertScore(order.getExpert().getId(),
                    order.getExpert().getScore() + scoreDTO.getScore());
            scoreDTO.setClient(order.getClient());
            scoreDTO.setOrder(order);
            scoreDTO.setExpert(offerService.findAcceptedOfferByOrderId(order.getId()).get().getExpert());
            scoreService.save(scoreDTO);
        }
    }

    @Override
    public void createScore(int score, String comment, Long orderId) {
        OrderDTO orderDTO = orderService.findById(orderId);
        Order order = orderRepository.findById(orderId).get();
        if (orderDTO == null) {
            throw new NotFoundTheOrderException("not found the order.");
        } else if (orderDTO.getOrderStatus() != OrderStatus.DONE) {
            throw new OrderStatusException("order has not get done yet.");
        } else if (order.getScore() != null) {
            throw new DuplicatedScoreException("order already has score.");
        } else if (!validation.isScoreValid(score)) {
            throw new ScoreRangeException("score is not valid");
        } else {
            expertRepository.updateExpertScore(order.getExpert().getId(),
                    order.getExpert().getScore() + score);
            ScoreDTO scoreDTO = new ScoreDTO();
            scoreDTO.setScore(score);
            scoreDTO.setComment(comment);
            scoreDTO.setClient(clientRepository.findById(orderDTO.getClientId()).get());
            scoreDTO.setOrder(order);
            scoreDTO.setExpert(offerService.findAcceptedOfferByOrderId(orderId).get().getExpert());
            scoreService.save(scoreDTO);
        }
    }

    @Override
    public void createScore(int score, Long orderId) {
        OrderDTO orderDTO = orderService.findById(orderId);
        Order order = orderRepository.findById(orderId).get();
        if (orderDTO == null) {
            throw new NotFoundTheOrderException("not found the order.");
        } else if (orderDTO.getOrderStatus() != OrderStatus.DONE) {
            throw new OrderStatusException("order has not get done yet.");
        } else if (order.getScore() != null) {
            throw new DuplicatedScoreException("order already has score.");
        } else if (!validation.isScoreValid(score)) {
            throw new ScoreRangeException("score is not valid");
        } else {
            expertRepository.updateExpertScore(order.getExpert().getId(),
                    order.getExpert().getScore() + score);
            ScoreDTO scoreDTO = new ScoreDTO();
            scoreDTO.setScore(score);
            scoreDTO.setClient(clientRepository.findById(orderDTO.getClientId()).get());
            scoreDTO.setOrder(order);
            scoreDTO.setExpert(offerService.findAcceptedOfferByOrderId(orderId).get().getExpert());
            scoreService.save(scoreDTO);
        }
    }

    @Override
    public void changeOrderStatusToPaid(Long orderId) {
        OrderDTO orderDTO = orderService.findById(orderId);
        Order order = orderRepository.findById(orderId).get();
        if (orderDTO == null) {
            throw new NotFoundTheOrderException("not found the order.");
        } else if (orderRepository.findById(orderId).get().getOrderStatus() != OrderStatus.DONE) {
            throw new OrderStatusException("order has not get done yet.");
        } else if (order.getScore() == null) {
            throw new NotFoundTheScoreException("order must have score.");
        } else {
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
    public List<ClientDTO> filterClient(FilterClientDTO filterClientDTO) {
        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> clientCriteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> clientRoot = clientCriteriaQuery.from(Client.class);
        createFilters(filterClientDTO, predicateList, criteriaBuilder, clientRoot);
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
    public void createFilters(FilterClientDTO filterClientDTO, List<Predicate> predicateList, CriteriaBuilder criteriaBuilder, Root<Client> clientRoot) {
        if (filterClientDTO.getFirstName() != null) {
            String firstName = "%" + filterClientDTO.getFirstName() + "%";
            predicateList.add(criteriaBuilder.like(clientRoot.get("firstName"), firstName));
        }
        if (filterClientDTO.getLastName() != null) {
            String lastName = "%" + filterClientDTO.getLastName() + "%";
            predicateList.add(criteriaBuilder.like(clientRoot.get("lastName"), lastName));
        }
        if (filterClientDTO.getEmail() != null) {
            String email = "%" + filterClientDTO.getEmail() + "%";
            predicateList.add(criteriaBuilder.like(clientRoot.get("email"), email));
        }
        if (filterClientDTO.getMinSignUpDate() != null && filterClientDTO.getMaxSignUpDate() != null){
            predicateList.add(criteriaBuilder.between(clientRoot.get("signUpDate"),
                    filterClientDTO.getMinSignUpDate(), filterClientDTO.getMaxSignUpDate()));
        }
        if (filterClientDTO.getMinOrdersNumber() != null && filterClientDTO.getMaxOrdersNumber() == null){
            int countOrders = orderRepository.countOrdersByClientEmail(String.valueOf(clientRoot.get("email")));
            predicateList.add(criteriaBuilder.ge(criteriaBuilder.literal(countOrders), filterClientDTO.getMinOrdersNumber()));
        }
        if (filterClientDTO.getMinOrdersNumber() == null && filterClientDTO.getMaxOrdersNumber() != null){
            int countOrders = orderRepository.countOrdersByClientEmail(String.valueOf(clientRoot.get("email")));
            predicateList.add(criteriaBuilder.le(criteriaBuilder.literal(countOrders), filterClientDTO.getMinOrdersNumber()));
        }
        if (filterClientDTO.getMinOrdersNumber() != null && filterClientDTO.getMaxOrdersNumber() != null){
            int countOrders = orderRepository.countOrdersByClientEmail(String.valueOf(clientRoot.get("email")));
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.literal(countOrders), filterClientDTO.getMinOrdersNumber()));
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.literal(countOrders), filterClientDTO.getMaxOrdersNumber()));
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
            throw new OrderStatusException("order has not get done yet.");
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

    @Override
    public void payByCard(CardDTO cardDTO) {
        Order order = orderRepository.findById(cardDTO.getOrderId()).get();
        Offer offer = offerService.findAcceptedOfferByOrderId(order.getId()).get();
        Expert expert = offer.getExpert();
        expertService.updateExpertWallet(expert.getId(),
                expert.getWallet().getBalance() + offer.getOfferedPrice() * 0.7);
        changeOrderStatusToPaid(order.getId());
    }

    @Override
    public List<OrderDTO> filterClientOrdersByOrderStatus(OrderStatus orderStatus){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((User) authentication.getPrincipal()).getId();
        System.out.println(id);
        return orderService.findNewOrdersByClientId(id, orderStatus);
    }
}
