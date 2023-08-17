package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ClientDTO;
import org.example.dto.OrderDTO;
import org.example.entity.Offer;
import org.example.entity.users.Client;
import org.example.repository.ClientRepository;
import org.example.service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/client-signup")
    @ResponseBody
    public void signUp(@RequestBody ClientDTO clientDTO) {
        clientService.clientSignUp(clientDTO);
    }

    @PostMapping("/edit-client-password")
    @ResponseBody
    public void editClientPassword(Long clientId, String newPassword){
        clientService.editClientPassword(clientId, newPassword);
    }

    @PostMapping("/create-order")
    @ResponseBody
    public void createOrder(@RequestBody OrderDTO orderDTO){
        clientService.createOrder(orderDTO);
    }

    @PostMapping("/accept-offer")
    @ResponseBody
    public void acceptOffer(Offer offer){
        clientService.acceptOffer(offer);
    }

    @PostMapping("/change-order-status-to-STARTED")
    @ResponseBody
    public void changeOrderStatusToStarted(Long orderId){
        clientService.changeOrderStatusToStarted(orderId);
    }

    @PostMapping("/change-order-status-to-DONE")
    @ResponseBody
    public void changeOrderStatusToDone(Long orderId){
        clientService.changeOrderStatusToDone(orderId);
    }
}
