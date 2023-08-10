package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.command.OrderCommand;
import org.example.converter.OrderCommandToOrderConverter;
import org.example.entity.Order;
import org.example.entity.enums.OrderStatus;
import org.example.exception.EmptyFieldException;
import org.example.exception.InvalidDateException;
import org.example.exception.InvalidPriceException;
import org.example.exception.InvalidTimeException;
import org.example.repository.OfferRepository;
import org.example.repository.OrderRepository;
import org.example.service.OrderService;
import org.example.validation.Validation;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> findOrdersByClientId(Long id) {
        return orderRepository.findOrdersByClientId(id);
    }

    @Override
    public List<Order> findOrdersByExpertId(Long id) {
        return orderRepository.findOrdersByExpertId(id);
    }

    @Override
    public List<Order> findOrdersBySubServiceId(Long id) {
        return orderRepository.findOrdersBySubServiceId(id);
    }

    @Override
    public List<Order> findNewOrdersBySubServiceId(Long id) {
        Predicate<Order> newOrder = order -> order.getOrderStatus() == OrderStatus.WAITING_FOR_EXPERT_OFFER;
        return findOrdersBySubServiceId(id).stream()
                .filter(newOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findOrdersByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findOrdersByOrderStatus(orderStatus);
    }
}
