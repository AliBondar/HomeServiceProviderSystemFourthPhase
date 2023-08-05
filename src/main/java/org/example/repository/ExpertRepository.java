package org.example.repository;


import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {

    Optional<Expert> findExpertByEmail(String email);

    Optional<Expert> findExpertByEmailAndPassword(String email, String password);

//    List<Expert> findWithExpertsStatus(UserStatus userStatus);
}
