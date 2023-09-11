package org.example.repository;


import org.example.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("select a.wallet from Admin a where a.email = :email and a.password = :password")
    Optional<Wallet> findAdminWalletByEmailAndPassword(String email, String password);

    @Query("select c.wallet from Client c where c.email = :email and c.password = :password")
    Optional<Wallet> findClientWalletByEmailAndPassword(String email, String password);

    @Query("select e.wallet from Expert e where e.email = :email and e.password = :password")
    Optional<Wallet> findExpertWalletByEmailAndPassword(String email, String password);

    @Query("select u.wallet from User u where u.email = :email and u.password = :password")
    Optional<Wallet> findUserWalletByEmailAndPassword(String email, String password);
}
