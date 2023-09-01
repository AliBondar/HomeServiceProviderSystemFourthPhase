package org.example.service;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.dto.ExpertDTO;
import org.example.dto.OfferDTO;
import org.example.dto.OrderDTO;
import org.example.dto.response.ExpertResponseDTO;
import org.example.entity.enums.OrderStatus;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ExpertService{

    void save(ExpertDTO expertDTO);

    void delete(ExpertDTO expertDTO);

    ExpertDTO findById(Long id);

    List<ExpertDTO> findAll();

    Optional<Expert> findExpertByEmail(String email);

    ExpertResponseDTO findExpertDTOByEmail(String email);

    Optional<Expert> findExpertByEmailAndPassword(String email, String password);

    void expertSignUp(ExpertDTO expertDTO) throws IOException;

    boolean isExpertEmailDuplicated(String email);

    List<Expert> findExpertsByUserStatus(UserStatus userStatus);

    void editExpertPassword(Long expertId, String password);

    void createOffer(OfferDTO offerDTO);

    List<ExpertResponseDTO> filterExpert(ExpertResponseDTO expertDTO);

    void createFilters(ExpertResponseDTO expertDTO, List<Predicate> predicateList, CriteriaBuilder criteriaBuilder, Root<Expert> expertRoot);

    void updateExpertWallet(Long expertId, double balance);

    void updateExpertScore(Long expertId, int score);

    List<OrderDTO> findOrdersByOrderStatus(OrderStatus orderStatus);
}
