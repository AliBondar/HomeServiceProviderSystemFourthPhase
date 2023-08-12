package org.example.mapper;

import org.example.dto.OrderDTO;
import org.example.entity.Order;
import org.example.entity.enums.OrderStatus;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;

public class OrderMapper implements BaseConverter<OrderDTO, Order> {


    @Override
    public Order convert(OrderDTO orderDTO) throws NoSuchAlgorithmException {
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
    public OrderDTO convert(Order order) throws NoSuchAlgorithmException {
        return null;
    }
}
