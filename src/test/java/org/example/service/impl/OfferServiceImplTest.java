package org.example.service.impl;

import org.example.repository.OrderRepository;
import org.example.service.OfferService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OfferServiceImplTest {

    @Autowired
    private OfferService offerService;

    @Autowired
    private OrderRepository orderRepository;
    @Test
    void findOffersByOrder() {
        assertEquals(12000,
                offerService.findOffersByOrder(orderRepository.findById(10L).get())
                        .get(0).getOfferedPrice());
    }

    @Test
    void findOffersByOrderId() {
        assertEquals(12000,
                offerService.findOffersByOrderId(orderRepository.findById(10L).get().getId())
                        .get(0).getOfferedPrice());
    }

    @Test
    void findAcceptedOfferByOrderId() {
        System.out.println("++++++++++++" + offerService.findAcceptedOfferByOrderId(orderRepository.findById(10L).get().getId())
                .get().getOfferedPrice());
        assertEquals(10000,
                offerService.findAcceptedOfferByOrderId(orderRepository.findById(10L).get().getId())
                        .get().getOfferedPrice());
    }
}