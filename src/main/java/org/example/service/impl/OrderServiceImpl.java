package org.example.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.OrderDTO;
import org.example.dto.request.FilterOrderDTO;
import org.example.entity.Order;
import org.example.entity.enums.OrderStatus;
import org.example.entity.users.User;
import org.example.mapper.OrderMapper;
import org.example.repository.OrderRepository;
import org.example.repository.SubServiceRepository;
import org.example.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final SubServiceRepository subServiceRepository;
    private final OrderMapper orderMapper;
    @PersistenceContext
    private EntityManager entityManager;

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
            for (Order order : orders) {
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
    public List<OrderDTO> findOrdersByClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((User) authentication.getPrincipal()).getId();
        List<Order> orders = orderRepository.findOrdersByClientId(id);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            orderDTOList.add(orderMapper.convert(order));
        }
        return orderDTOList;
    }

    @Override
    public List<Order> findOrdersBySubServiceId(Long id) {
        return orderRepository.findOrdersBySubServiceId(id);
    }

    @Override
    public List<OrderDTO> findNewOrdersBySubServiceId(Long id) {
        Predicate<Order> newOrder = order -> order.getOrderStatus() == OrderStatus.WAITING_FOR_EXPERT_OFFER;
        List<OrderDTO> orderDTOList = new ArrayList<>();
        List<Order> orders = findOrdersBySubServiceId(id).stream().filter(newOrder).toList();
        for (Order order : orders) {
            orderDTOList.add(orderMapper.convert(order));
        }
        return orderDTOList;
    }

    @Override
    public List<Order> findOrdersByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findOrdersByOrderStatus(orderStatus);
    }

    @Override
    public List<OrderDTO> filterOrder(FilterOrderDTO filterOrderDTO) {
        List<jakarta.persistence.criteria.Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> orderCriteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = orderCriteriaQuery.from(Order.class);

        createFilters(filterOrderDTO, predicateList, criteriaBuilder, orderRoot);
        jakarta.persistence.criteria.Predicate[] predicates = new jakarta.persistence.criteria.Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        orderCriteriaQuery.select(orderRoot).where(predicates);
        List<Order> resultList = entityManager.createQuery(orderCriteriaQuery).getResultList();

        List<OrderDTO> filterOrderResponseDTOS = new ArrayList<>();
        for (Order order : resultList)
            filterOrderResponseDTOS.add(orderMapper.convert(order));
        return filterOrderResponseDTOS;
    }

    private void createFilters(FilterOrderDTO filterOrderDTO, List<jakarta.persistence.criteria.Predicate> predicateList, CriteriaBuilder criteriaBuilder, Root<Order> orderRoot) {

        if (filterOrderDTO.getDescription() != null) {
            String description = "%" + filterOrderDTO.getDescription() + "%";
            predicateList.add(criteriaBuilder.like(orderRoot.get("description"), description));
        }
        if (filterOrderDTO.getOrderStatus() != null) {
            predicateList.add(criteriaBuilder.equal(orderRoot.get("orderStatus"), filterOrderDTO.getOrderStatus()));
        }
        if (filterOrderDTO.getSubServiceId() != null) {
            predicateList.add(criteriaBuilder.equal(orderRoot.get("subService"), subServiceRepository.findById(filterOrderDTO.getSubServiceId()).get()));
        }
        if (filterOrderDTO.getMinClientOfferedPrice() == null && filterOrderDTO.getMaxClientOfferedPrice() != null) {
            predicateList.add(criteriaBuilder.lt(orderRoot.get("clientOfferedPrice"), filterOrderDTO.getMaxClientOfferedPrice()));
        }
        if (filterOrderDTO.getMinClientOfferedPrice() != null && filterOrderDTO.getMaxClientOfferedPrice() == null) {
            predicateList.add(criteriaBuilder.gt(orderRoot.get("clientOfferedPrice"), filterOrderDTO.getMinClientOfferedPrice()));
        }
        if (filterOrderDTO.getMinClientOfferedPrice() != null && filterOrderDTO.getMaxClientOfferedPrice() != null) {
            predicateList.add(criteriaBuilder.between(orderRoot.get("clientOfferedPrice"),
                    filterOrderDTO.getMinClientOfferedPrice(),
                    filterOrderDTO.getMaxClientOfferedPrice()));
        }
        if (filterOrderDTO.getMinClientOfferedWorkDuration() == null && filterOrderDTO.getMaxClientOfferedWorkDuration() != null) {
            predicateList.add(criteriaBuilder.lt(orderRoot.get("clientOfferedWorkDuration"), filterOrderDTO.getMaxClientOfferedWorkDuration()));
        }
        if (filterOrderDTO.getMinClientOfferedWorkDuration() != null && filterOrderDTO.getMaxClientOfferedWorkDuration() == null) {
            predicateList.add(criteriaBuilder.gt(orderRoot.get("clientOfferedWorkDuration"), filterOrderDTO.getMinClientOfferedWorkDuration()));
        }
        if (filterOrderDTO.getMinClientOfferedWorkDuration() != null && filterOrderDTO.getMaxClientOfferedWorkDuration() != null) {
            predicateList.add(criteriaBuilder.between(orderRoot.get("clientOfferedWorkDuration"),
                    filterOrderDTO.getMinClientOfferedWorkDuration(),
                    filterOrderDTO.getMaxClientOfferedWorkDuration()));
        }
    }

    @Override
    public int countClientOrders(Long id) {
        return orderRepository.countOrdersByClientId(id);
    }
}
