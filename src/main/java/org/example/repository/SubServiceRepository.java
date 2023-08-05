package org.example.repository;


import org.example.entity.Service;
import org.example.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SubServiceRepository extends JpaRepository<SubService, Long> {

//    List<SubService> findWithServiceId(Long id);

//    Optional<SubService> findByNameAndService(String description, Service service);

//    List<SubService> findByExpertId(Long id);

//    List<SubService> findByServiceId(Long id);
}
