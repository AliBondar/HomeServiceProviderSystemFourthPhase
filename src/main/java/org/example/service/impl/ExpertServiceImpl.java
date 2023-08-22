package org.example.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.ExpertDTO;
import org.example.dto.OfferDTO;
import org.example.mapper.ExpertMapper;
import org.example.entity.Order;
import org.example.entity.Wallet;
import org.example.entity.enums.OrderStatus;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;
import org.example.exception.*;
import org.example.repository.ExpertRepository;
import org.example.repository.OrderRepository;
import org.example.repository.WalletRepository;
import org.example.security.PasswordHash;
import org.example.service.ExpertService;
import org.example.service.OfferService;
import org.example.validation.Validation;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final WalletRepository walletRepository;
    private final OrderRepository orderRepository;
    private final OfferService offerService;
    @PersistenceContext
    private EntityManager entityManager;
    private final ExpertMapper expertMapper = new ExpertMapper();

    @Override
    public void save(ExpertDTO expertDTO) {
        Expert expert = expertMapper.convert(expertDTO);
        expertRepository.save(expert);
    }

    @Override
    public void delete(ExpertDTO expertDTO) {
        Expert expert = expertMapper.convert(expertDTO);
        expertRepository.delete(expert);
    }

    @Override
    public ExpertDTO findById(Long id) {
        Optional<Expert> expert = expertRepository.findById(id);
        return expert.map(expertMapper::convert).orElse(null);
    }

    @Override
    public List<ExpertDTO> findAll() {
        List<Expert> experts = expertRepository.findAll();
        List<ExpertDTO> expertDTOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(experts)) return null;
        else {
            for (Expert expert : experts) {
                expertDTOList.add(expertMapper.convert(expert));
            }
            return expertDTOList;
        }
    }

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
        String[] path = expertDTO.getImageData().getPath().split("\\.");
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
            Wallet wallet = new Wallet();
            wallet.setBalance(0);
            walletRepository.save(wallet);
            expertDTO.setWallet(wallet);
            this.save(expertDTO);
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
        } else {
            offerService.save(offerDTO);
            Order order = orderRepository.findById(offerDTO.getOrder().getId()).get();
            order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_CHOOSE);
            orderRepository.save(order);
        }
    }

    @Override
    public List<ExpertDTO> filterExpert(ExpertDTO expertDTO) {
        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Expert> expertCriteriaQuery = criteriaBuilder.createQuery(Expert.class);
        Root<Expert> expertRoot = expertCriteriaQuery.from(Expert.class);
        createFilters(expertDTO, predicateList, criteriaBuilder, expertRoot);
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        expertCriteriaQuery.select(expertRoot).where(predicates);
        List<Expert> resultList = entityManager.createQuery(expertCriteriaQuery).getResultList();
        List<ExpertDTO> expertDTOList = new ArrayList<>();
        for (Expert expert : resultList){
            expertDTOList.add(expertMapper.convert(expert));
        }
        return expertDTOList;
    }

    @Override
    public void createFilters(ExpertDTO expertDTO, List<Predicate> predicateList, CriteriaBuilder criteriaBuilder, Root<Expert> expertRoot) {
        if (expertDTO.getFirstName() != null) {
            String firstName = "%" + expertDTO.getFirstName() + "%";
            predicateList.add(criteriaBuilder.like(expertRoot.get("firstName"), firstName));
        }
        if (expertDTO.getLastName() != null) {
            String lastName = "%" + expertDTO.getLastName() + "%";
            predicateList.add(criteriaBuilder.like(expertRoot.get("lastName"), lastName));
        }
        if (expertDTO.getEmail() != null) {
            String email = "%" + expertDTO.getEmail() + "%";
            predicateList.add(criteriaBuilder.like(expertRoot.get("email"), email));
        }
        if (expertDTO.getUserStatus() != null) {
            predicateList.add(criteriaBuilder.equal(expertRoot.get("userStatus"), expertDTO.getUserStatus()));
        }
    }
}
