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

    @PutMapping("/edit-client-password/{clientId}/{newPassword}")
    @ResponseBody
    public void editClientPassword(@PathVariable Long clientId,@PathVariable String newPassword) {
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

    @PostMapping("/change-order-status-to-STARTED/{orderId}")
    @ResponseBody
    public void changeOrderStatusToStarted(@PathVariable Long orderId) {
        clientService.changeOrderStatusToStarted(orderId);
    }

    @PostMapping("/change-order-status-to-DONE/{orderId}")
    @ResponseBody
    public void changeOrderStatusToDone(@PathVariable Long orderId) {
        clientService.changeOrderStatusToDone(orderId);
    }

    @GetMapping("/show-orders-history/{id}")
    public List<Order> findOrdersByClientId(@PathVariable Long id) {
        return orderService.findOrdersByClientId(id);
    }

    @GetMapping("/show-offers-by-order/{id}")
    public List<Offer> findOffersByOrderId(@PathVariable Long id) {
        return offerService.findOffersByOrderId(id);
    }

    @GetMapping("/show-all-services")
    public List<ServiceDTO> findAllServices() {
        return serviceService.findAll();
    }

    @GetMapping("/show-all-sub-services/{name}")
    public List<SubService> findSubServicesByServiceName(@PathVariable String name) {
        return subServiceService.findSubServicesByServiceName(name);
    }

    @GetMapping("/show-client-wallet/{email}/{password}")
    public WalletDTO findClientWalletByEmailAndPassword(@PathVariable String email,@PathVariable String password) {
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
