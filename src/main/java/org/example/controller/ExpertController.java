package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ExpertDTO;
import org.example.dto.OfferDTO;
import org.example.dto.OrderDTO;
import org.example.dto.WalletDTO;
import org.example.entity.Offer;
import org.example.entity.Order;
import org.example.entity.SubService;
import org.example.entity.enums.OrderStatus;
import org.example.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping( "/api/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;
    private final OrderService orderService;
    private final SubServiceService subServiceService;
    private final OfferService offerService;
    private final WalletService walletService;

//    @PostMapping("/expert-signup")
//    public void signup(@RequestBody ExpertDTO expertDTO) {
//        try {expert_sub_service_list
//            expertService.expertSignUp(expertDTO);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @PutMapping("/edit-expert-password/{expertId}/{password}")
    public ResponseEntity<String> editExpertPassword(@PathVariable Long expertId, @PathVariable String password) {
        expertService.editExpertPassword(expertId, password);
        return ResponseEntity.ok().body("Password has been edited successfully.");
    }

    @PostMapping("/create-offer")
    public ResponseEntity<String> createOffer(@RequestBody OfferDTO offerDTO) {
        expertService.createOffer(offerDTO);
        return ResponseEntity.ok().body("Offer has been submitted successfully.");
    }

    @GetMapping("/show-new-orders/{id}")
    public List<OrderDTO> findNewOrdersBySubServiceId(@PathVariable Long id) {
        return orderService.findNewOrdersBySubServiceId(id);
    }

    @GetMapping("/show-sub-services-by-expert/{id}")
    public List<SubService> findSubServicesByExpertId(@PathVariable Long id) {
        return subServiceService.findByExpertId(id);
    }

    @GetMapping("/show-accepted-offers-by-expert/{id}")
    public List<Offer> findAcceptedOffersByExpertId(@PathVariable Long id){
        return offerService.findAcceptedOffersByExpertId(id);
    }

    @GetMapping("/show-expert-score/{id}")
    public int showExpertScore(@PathVariable Long id){
        return expertService.findById(id).getScore();
    }

    @GetMapping("/show-WAITING-orders")
    public List<OrderDTO> findWaitingOrders(){
        return expertService.findOrdersByOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER);
    }

    @GetMapping("/show-expert-wallet")
    public WalletDTO findExpertWallet(){
        return walletService.findUserWallet().get();
    }

}
