package org.example.repository;


import org.example.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    List<Order> findOrdersByClientId(Long id);
//
//    List<Order> findOrdersByExpertId(Long id);
//
//    List<Order> findOrdersBySubServiceId(Long id);
}
