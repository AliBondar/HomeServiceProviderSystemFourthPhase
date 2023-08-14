package org.example.mapper;

import org.example.dto.OrderDTO;
import org.example.entity.Order;
import org.example.entity.enums.OrderStatus;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;

public class OrderMapper implements BaseMapper<OrderDTO, Order> {


    @Override
    public Order convert(OrderDTO orderDTO) {
        Order order = new Order();
        order.setDescription(orderDTO.getDescription());
        order.setLocalDate(orderDTO.getLocalDate());
        order.setTime(Time.valueOf(orderDTO.getLocalTime()));
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER);
        order.setPaid(0);
        order.setClient(orderDTO.getClient());
        order.setSubService(orderDTO.getSubService());
        order.setClientOfferedPrice(orderDTO.getClientOfferedPrice());
        order.setClientOfferedWorkDuration(orderDTO.getClientOfferedWorkDuration());
        return order;
    }

    @Override
    public OrderDTO convert(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDescription(order.getDescription());
        orderDTO.setLocalDate(order.getLocalDate());
        orderDTO.setLocalTime(order.getTime().toLocalTime());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setPaid(order.getPaid());
        orderDTO.setClient(order.getClient());
        orderDTO.setSubService(order.getSubService());
        orderDTO.setClientOfferedPrice(order.getClientOfferedPrice());
        orderDTO.setClientOfferedWorkDuration(order.getClientOfferedWorkDuration());
        return orderDTO;
    }
}
