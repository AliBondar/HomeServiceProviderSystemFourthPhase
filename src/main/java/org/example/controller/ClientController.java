package org.example.controller;

import cn.apiclub.captcha.Captcha;
import lombok.RequiredArgsConstructor;
import org.example.captcha.CaptchaUtils;
import org.example.dto.*;
import org.example.entity.*;
import org.example.entity.users.Client;
import org.example.repository.ClientRepository;
import org.example.service.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final OrderService orderService;
    private final OfferService offerService;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;
    private final WalletService walletService;

    @PostMapping("/client-signup")
    @ResponseBody
    public void signUp(@RequestBody ClientDTO clientDTO) {
        clientService.clientSignUp(clientDTO);
    }

    @PostMapping("/edit-client-password")
    @ResponseBody
    public void editClientPassword(Long clientId, String newPassword) {
        clientService.editClientPassword(clientId, newPassword);
    }

    @PostMapping("/create-order")
    @ResponseBody
    public void createOrder(@RequestBody OrderDTO orderDTO) {
        clientService.createOrder(orderDTO);
    }

    @PostMapping("/accept-offer")
    @ResponseBody
    public void acceptOffer(Offer offer) {
        clientService.acceptOffer(offer);
    }

    @PostMapping("/change-order-status-to-STARTED")
    @ResponseBody
    public void changeOrderStatusToStarted(Long orderId) {
        clientService.changeOrderStatusToStarted(orderId);
    }

    @PostMapping("/change-order-status-to-DONE")
    @ResponseBody
    public void changeOrderStatusToDone(Long orderId) {
        clientService.changeOrderStatusToDone(orderId);
    }

    @GetMapping("/show-orders-history")
    public List<Order> findOrdersByClientId(Long id) {
        return orderService.findOrdersByClientId(id);
    }

    @GetMapping("/show-offers-by-order")
    public List<Offer> findOffersByOrderId(Long id) {
        return offerService.findOffersByOrderId(id);
    }

    @GetMapping("/show-all-services")
    public List<ServiceDTO> findAllServices() {
        return serviceService.findAll();
    }

    @GetMapping("/show-all-sub-services")
    public List<SubService> findSubServicesByServiceName(String name) {
        return subServiceService.findSubServicesByServiceName(name);
    }

    @GetMapping("/show-client-wallet")
    public WalletDTO findClientWalletByEmailAndPassword(String email, String password) {
        return walletService.findClientWalletByEmailAndPassword(email, password).get();
    }

    @PutMapping("/pay-by-wallet/{orderId}/{clientId}")
    public void payByWallet(@PathVariable Long orderId, @PathVariable Long clientId) {
        clientService.payByWallet(orderId, clientId);
    }

    @PostMapping("/create-score")
    public void createScore(@RequestBody ScoreDTO scoreDTO){
        clientService.createScore(scoreDTO);
    }

    @GetMapping("/payment")
    public ModelAndView showRegister(Model model) {
       CardDTO card = new CardDTO();
        setupCaptcha(card);
        model.addAttribute("card", card);

        return new ModelAndView("payment.html");
    }

    private void setupCaptcha(CardDTO card) {
        Captcha captcha = CaptchaUtils.createCaptcha(200, 50);
        card.setHidden(captcha.getAnswer());
        card.setCaptcha("");
        card.setImage(CaptchaUtils.encodeBase64(captcha));
    }


}
