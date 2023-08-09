package org.example.service.impl;

import org.example.entity.Offer;
import org.example.entity.Order;
import org.example.repository.OfferRepository;
import org.example.repository.OrderRepository;
import org.example.service.ExpertService;
import org.example.service.OfferService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OfferServiceImplTest {

    @Autowired
    private OfferService offerService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private ExpertService expertService;

    @Test
    @org.junit.jupiter.api.Order(1)
    void findOffersByOrder() {
        Order order = new Order();
        order.setTime(Time.valueOf(LocalTime.now()));
        order.setLocalDate(LocalDate.now());
        order.setClientOfferedPrice(10000);
        orderRepository.save(order);
        System.out.println(order.getId());
        Offer offer = new Offer();
        offer.setOfferedPrice(12000);
        offer.setOrder(order);
        offer.setAccepted(false);
        offerRepository.save(offer);
        assertEquals(12000,
                offerService.findOffersByOrder(orderRepository.findById(100L).get())
                        .get(0).getOfferedPrice());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void findOffersByOrderId() {
        assertEquals(12000,
                offerService.findOffersByOrderId(orderRepository.findById(100L).get().getId())
                        .get(0).getOfferedPrice());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findAcceptedOfferByOrderId() {
        assertEquals(10000,
                offerService.findAcceptedOfferByOrderId(orderRepository.findById(100L).get().getId())
                        .get().getOfferedPrice());
    }
}