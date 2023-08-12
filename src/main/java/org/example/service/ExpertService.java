package org.example.service;


import org.example.dto.ExpertDTO;
import org.example.dto.OfferDTO;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ExpertService{

    Optional<Expert> findExpertByEmail(String email);

    Optional<Expert> findExpertByEmailAndPassword(String email, String password);

    void expertSignUp(ExpertDTO expertDTO) throws IOException;

    boolean isExpertEmailDuplicated(String email);

    List<Expert> findExpertsByUserStatus(UserStatus userStatus);

    void editExpertPassword(Long expertId, String password);

    void createOffer(OfferDTO offerDTO);
}
