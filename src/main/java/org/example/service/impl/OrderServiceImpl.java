package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.OrderDTO;
import org.example.entity.Order;
import org.example.entity.enums.OrderStatus;
import org.example.mapper.OrderMapper;
import org.example.repository.OrderRepository;
import org.example.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper = new OrderMapper();

    @Override
    public void save(OrderDTO orderDTO) {
        Order order = orderMapper.convert(orderDTO);
        orderRepository.save(order);
    }

    @Override
    public void delete(OrderDTO orderDTO) {
        Order order = orderMapper.convert(orderDTO);
        orderRepository.delete(order);
    }

    @Override
    public OrderDTO findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(orderMapper::convert).orElse(null);
    }

    @Override
    public List<OrderDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(orders)) return null;
        else {
            for (Order order : orders){
                orderDTOList.add(orderMapper.convert(order));
            }
            return orderDTOList;
        }
    }

    @Override
    public List<Order> findOrdersByClientId(Long id) {
        return orderRepository.findOrdersByClientId(id);
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
