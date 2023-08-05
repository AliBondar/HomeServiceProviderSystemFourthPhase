package org.example.repository;


import org.example.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    Optional<Service> findServiceByName(String name);
}
