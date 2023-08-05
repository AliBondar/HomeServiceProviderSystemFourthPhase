package org.example.converter;

import org.example.command.OrderCommand;
import org.example.entity.Order;
import org.example.entity.enums.OrderStatus;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;

public class OrderCommandToOrderConverter implements BaseConverter<OrderCommand, Order> {


    @Override
    public Order convert(OrderCommand orderCommand) throws NoSuchAlgorithmException {
        Order order = new Order();
        order.setDescription(orderCommand.getDescription());
        order.setLocalDate(orderCommand.getLocalDate());
        order.setTime(Time.valueOf(orderCommand.getLocalTime()));
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER);
        order.setPaid(0);
        order.setClient(orderCommand.getClient());
        order.setSubService(orderCommand.getSubService());
        order.setClientOfferedPrice(orderCommand.getClientOfferedPrice());
        order.setClientOfferedWorkDuration(orderCommand.getClientOfferedWorkDuration());
        return order;
    }

    @Override
    public OrderCommand convert(Order order) throws NoSuchAlgorithmException {
        return null;
    }
}
