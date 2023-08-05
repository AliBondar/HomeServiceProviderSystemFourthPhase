package org.example.repository;



import org.example.entity.users.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByEmail(String email);

    Optional<Client> findClientByEmailAndPassword(String email, String password);



}
