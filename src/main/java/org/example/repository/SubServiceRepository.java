package org.example.repository;


import org.example.entity.Service;
import org.example.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SubServiceRepository extends JpaRepository<SubService, Long> {

    Optional<SubService> findByDescriptionAndService(String description, Service service);

    @Query("select s from SubService s join s.expertList e where e.id = :id")
    List<SubService> findByExpertId(Long id);

    @Query("select s from SubService s where s.service.id = :id")
    List<SubService> findSubServicesByServiceId(Long id);

    List<SubService> findSubServicesByService(Service service);

    Optional<SubService> findSubServiceByDescription(String description);

    List<SubService> findSubServicesByServiceName(String name);
}
