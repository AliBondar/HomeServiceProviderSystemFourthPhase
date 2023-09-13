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

    @Query("SELECT e FROM Expert e WHERE EXISTS (SELECT o FROM e.orderList o WHERE o.orderStatus = 'DONE')")
    List<Expert> findExpertsByDoneOrdersNumber();

    @Modifying
    @Query("update  Expert e set e.wallet.balance = :balance where e.id = :expertId ")
    void updateExpertWallet(Long expertId, double balance);

    @Modifying
    @Query("update Expert e set e.score = :score where e.id = :expertId")
    void updateExpertScore(Long expertId, int score);
}
