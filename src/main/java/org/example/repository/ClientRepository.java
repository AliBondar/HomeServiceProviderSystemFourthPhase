package org.example.repository;



import org.example.entity.users.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByEmail(String email);

    Optional<Client> findClientByEmailAndPassword(String email, String password);

    @Modifying
    @Query("update  Client  c set c.wallet.balance = :balance where c.id = :clientId ")
    void updateClientWallet(Long clientId, double balance);

}
