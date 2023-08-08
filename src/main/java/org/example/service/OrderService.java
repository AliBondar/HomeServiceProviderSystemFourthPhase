package org.example.service;


import org.example.command.OrderCommand;
import org.example.entity.Order;
import org.example.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    List<Order> findOrdersByClientId(Long id);

    List<Order> findOrdersByExpertId(Long id);

    List<Order> findOrdersBySubServiceId(Long id);

    List<Order> findNewOrdersBySubServiceId(Long id);

    void createOrder(OrderCommand orderCommand);

    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus);
}
