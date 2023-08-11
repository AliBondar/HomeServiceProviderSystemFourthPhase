package org.example.service.impl;

import net.bytebuddy.asm.Advice;
import org.example.entity.Order;
import org.example.entity.SubService;
import org.example.entity.enums.OrderStatus;
import org.example.entity.users.Client;
import org.example.repository.ClientRepository;
import org.example.repository.ExpertRepository;
import org.example.repository.OrderRepository;
import org.example.repository.SubServiceRepository;
import org.example.service.ClientService;
import org.example.service.OrderService;
import org.example.service.SubServiceService;
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
class OrderServiceImplTest {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SubServiceRepository subServiceRepository;
    @Autowired
    private ExpertRepository expertRepository;
    @Autowired
    private SubServiceService subServiceService;

    @Test
    void findOrdersByClientId() {
        SubService subService = new SubService();
        subService.setDescription("testSubService");
        subService.setBasePrice(8000);
        subServiceRepository.save(subService);
        Client client = new Client();
        client.setFirstName("testClientFName");
        client.setLastName("testClientLName");
        client.setEmail("client@gmail.com");
        client.setPassword("@Client1234");
        clientRepository.save(client);
        Order order = new Order();
        order.setTime(Time.valueOf(LocalTime.now()));
        order.setLocalDate(LocalDate.now());
        order.setClientOfferedPrice(10000);
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER);
        order.setClient(client);
        order.setSubService(subService);
        orderRepository.save(order);
        assertEquals(
                10000,
                orderService.findOrdersByClientId(client.getId()).get(0).getClientOfferedPrice()
        );
    }

    @Test
    void findOrdersBySubServiceId() {
        assertNotNull(orderService.findOrdersBySubServiceId(subServiceService.findSubServiceByDescription("testSubService").get().getId()));
    }

    @Test
    void findNewOrdersBySubServiceId() {
        assertNotNull(orderService.findOrdersBySubServiceId(subServiceService.findSubServiceByDescription("testSubService").get().getId()));
    }

    @Test
    void findOrdersByOrderStatus() {
        assertNotNull(orderService.findOrdersByOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER));
    }
}