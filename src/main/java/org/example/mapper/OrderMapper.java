package org.example.mapper;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.dto.OrderDTO;
import org.example.entity.Order;
import org.example.entity.enums.OrderStatus;
import org.example.repository.ClientRepository;
import org.example.repository.OrderRepository;
import org.example.repository.SubServiceRepository;
import org.example.service.impl.ClientServiceImpl;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;

@Component
@RequiredArgsConstructor
public class OrderMapper implements BaseMapper<OrderDTO, Order> {

    final ClientRepository clientRepository;
    final SubServiceRepository subServiceRepository;

    @Override
    public Order convert(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setDescription(orderDTO.getDescription());
        order.setLocalDate(orderDTO.getLocalDate());
        order.setTime(Time.valueOf(orderDTO.getLocalTime()));
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER);
        order.setPaid(0);
        order.setClient(clientRepository.findById(orderDTO.getClientId()).get());
        order.setSubService(subServiceRepository.findById(orderDTO.getSubServiceId()).get());
        order.setClientOfferedPrice(orderDTO.getClientOfferedPrice());
        order.setClientOfferedWorkDuration(orderDTO.getClientOfferedWorkDuration());
        return order;
    }

    @Override
    public OrderDTO convert(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setDescription(order.getDescription());
        orderDTO.setLocalDate(order.getLocalDate());
        orderDTO.setLocalTime(order.getTime().toLocalTime());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setPaid(order.getPaid());
        orderDTO.setClientId(order.getClient().getId());
        orderDTO.setSubServiceId(order.getSubService().getId());
        orderDTO.setClientOfferedPrice(order.getClientOfferedPrice());
        orderDTO.setClientOfferedWorkDuration(order.getClientOfferedWorkDuration());
        return orderDTO;
    }
}
