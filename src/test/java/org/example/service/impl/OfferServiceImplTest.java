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
        Offer offer = new Offer();
        offer.setOfferedPrice(12000);
        offer.setOrder(order);
        offer.setAccepted(false);
        offerRepository.save(offer);
        assertNotNull(offerService.findOffersByOrder(orderRepository.findById(order.getId()).get())
                .get(0));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void findOffersByOrderId() {
        Order order = new Order();
        order.setTime(Time.valueOf(LocalTime.now()));
        order.setLocalDate(LocalDate.now());
        order.setClientOfferedPrice(15000);
        orderRepository.save(order);
        Offer offer = new Offer();
        offer.setOfferedPrice(13000);
        offer.setOrder(order);
        offer.setAccepted(false);
        offerRepository.save(offer);
        assertNotNull(offerService.findOffersByOrderId(order.getId()).get(0));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findAcceptedOfferByOrderId() {
        Order order = new Order();
        order.setTime(Time.valueOf(LocalTime.now()));
        order.setLocalDate(LocalDate.now());
        order.setClientOfferedPrice(20000);
        orderRepository.save(order);
        Offer offer = new Offer();
        offer.setOfferedPrice(14000);
        offer.setOrder(order);
        offer.setAccepted(true);
        offerRepository.save(offer);
        assertNotNull(offerService.findAcceptedOfferByOrderId(order.getId()).get());
    }
}