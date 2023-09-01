package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ExpertDTO;
import org.example.dto.OfferDTO;
import org.example.dto.OrderDTO;
import org.example.entity.Offer;
import org.example.entity.Order;
import org.example.entity.SubService;
import org.example.entity.enums.OrderStatus;
import org.example.service.ExpertService;
import org.example.service.OfferService;
import org.example.service.OrderService;
import org.example.service.SubServiceService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;
    private final OrderService orderService;
    private final SubServiceService subServiceService;
    private final OfferService offerService;

    @PostMapping("/expert-signup")
    @ResponseBody
    public void signup(@ModelAttribute ExpertDTO expertDTO) {
        try {
            expertService.expertSignUp(expertDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/edit-expert-password")
    @ResponseBody
    public void editExpertPassword(@RequestBody Long expertId, @RequestBody String password) {
        expertService.editExpertPassword(expertId, password);
    }

    @PostMapping("/create-offer")
    public void createOffer(@RequestBody OfferDTO offerDTO) {
        expertService.createOffer(offerDTO);
    }

    @GetMapping("/show-new-orders")
    public List<Order> findNewOrdersBySubServiceId(Long id) {
        return orderService.findNewOrdersBySubServiceId(id);
    }

    @GetMapping("/show-sub-services-by-expert")
    public List<SubService> findSubServicesByExpertId(Long id) {
        return subServiceService.findByExpertId(id);
    }

    @GetMapping("/show-accepted-offers-by-expert")
    public List<Offer> findAcceptedOffersByExpertId(Long id){
        return offerService.findAcceptedOffersByExpertId(id);
    }

    @GetMapping("/show-expert-score")
    public int showExpertScore(Long id){
        return expertService.findById(id).getScore();
    }

    @GetMapping("/show-WAITING-orders")
    public List<OrderDTO> findWaitingOrders(){
        return expertService.findOrdersByOrderStatus(OrderStatus.WAITING_FOR_EXPERT_CHOOSE);
    }

}
