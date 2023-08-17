package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ExpertDTO;
import org.example.dto.OfferDTO;
import org.example.service.ExpertService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/expert")
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertService expertService;

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


}
