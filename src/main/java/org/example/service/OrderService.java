package org.example.service;


import org.example.dto.OrderDTO;
import org.example.entity.Order;
import org.example.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    void save(OrderDTO orderDTO);

    void delete(OrderDTO orderDTO);

    OrderDTO findById(Long id);

    List<OrderDTO> findAll();

    List<Order> findOrdersByClientId(Long id);

    List<Order> findOrdersBySubServiceId(Long id);

    List<OrderDTO> findNewOrdersBySubServiceId(Long id);

    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus);

    List<OrderDTO> ordersFilter(OrderDTO orderDTO);
}
