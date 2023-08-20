package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ExpertDTO;
import org.example.dto.OfferDTO;
import org.example.dto.OrderDTO;
import org.example.service.ExpertService;
import org.example.service.OrderService;
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

    @PostMapping("/expert-signup")
    @ResponseBody
    public void signup(@RequestBody ExpertDTO expertDTO) {
        try {
            expertService.expertSignUp(expertDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/edit-expert-password")
    @ResponseBody
    public void editExpertPassword(@RequestBody Long expertId,@RequestBody String password) {
        expertService.editExpertPassword(expertId, password);
    }

    @PostMapping("/create-offer")
    @ResponseBody
    public void createOffer(@RequestBody OfferDTO offerDTO) {
        expertService.createOffer(offerDTO);
    }

    @GetMapping("/show-new-orders")
    public List<OrderDTO> findNewOrdersBySubServiceId(Long id){
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderService.findNewOrdersBySubServiceId(id);
        return orderDTOList;
    }
}
