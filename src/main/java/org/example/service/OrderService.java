package org.example.service;


import org.example.entity.Order;
import org.example.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    List<Order> findOrdersByClientId(Long id);

    List<Order> findOrdersBySubServiceId(Long id);

    List<Order> findNewOrdersBySubServiceId(Long id);

    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus);
}
