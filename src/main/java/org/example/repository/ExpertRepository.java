package org.example.repository;


import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {

    Optional<Expert> findExpertByEmail(String email);

    Optional<Expert> findExpertByEmailAndPassword(String email, String password);

    List<Expert> findExpertsByUserStatus(UserStatus userStatus);

    @Modifying
    @Query("update  Expert e set e.wallet.balance = :balance where e.id = :expertId ")
    void updateExpertWallet(Long expertId, double balance);

    @Modifying
    @Query("update Expert  e set e.score = :score where e.id = :expertId")
    void updateExpertScore(Long expertId, int score);
}
