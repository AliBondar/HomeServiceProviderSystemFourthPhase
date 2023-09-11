package org.example.controller;

import cn.apiclub.captcha.Captcha;
import lombok.RequiredArgsConstructor;
import org.example.captcha.CaptchaUtils;
import org.example.dto.*;
import org.example.entity.*;
import org.example.entity.users.Client;
import org.example.entity.users.User;
import org.example.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

//    @PostMapping("/client-signup")
//    @ResponseBody
//    public void signUp(@RequestBody ClientDTO clientDTO) {
//        try {
//            clientService.clientSignUp(clientDTO);
//        } catch (jakarta.mail.SendFailedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @PutMapping("/edit-client-password/{newPassword}/{clientId}")
    public void editClientPassword(@PathVariable String newPassword, @PathVariable Long clientId) {
        clientService.editClientPassword(clientId, newPassword);
    }

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        clientService.createOrder(orderDTO);
        return ResponseEntity.ok().body("Order has been submitted successfully.");
    }

    @PutMapping("/accept-offer/{id}")
    public ResponseEntity<String> acceptOffer(@PathVariable Long id) {
        clientService.acceptOffer(id);
        return ResponseEntity.ok().body("Offer accepted successfully.");
    }

    @PutMapping("/change-order-status-to-STARTED/{orderId}")
    public ResponseEntity<String> changeOrderStatusToStarted(@PathVariable Long orderId) {
        clientService.changeOrderStatusToStarted(orderId);
        return ResponseEntity.ok().body("Order status has been changed into STARTED.");
    }

    @PutMapping("/change-order-status-to-DONE/{orderId}")
    public ResponseEntity<String> changeOrderStatusToDone(@PathVariable Long orderId) {
        clientService.changeOrderStatusToDone(orderId);
        return ResponseEntity.ok().body("Order status has been changed into DONE");
    }

//    @GetMapping("/show-orders-history/{id}")
//    public List<Order> findOrdersByClientId(@PathVariable Long id) {
//        return orderService.findOrdersByClientId(id);
//    }

    @GetMapping("/show-orders-history")
    public List<OrderDTO> findOrdersByClientId() {
        return orderService.findOrdersByClient();
    }

    @GetMapping("/show-offers-by-order/{id}")
    public List<OfferDTO> findOffersByOrderId(@PathVariable Long id) {
        return offerService.findOffersByOrderId(id);
    }

    @GetMapping("/show-all-services")
    public List<ServiceDTO> findAllServices() {
        return serviceService.findAll();
    }

    @GetMapping("/show-all-sub-services")
    public List<SubServiceDTO> findAllSubServices() {
        return subServiceService.findAll();
    }

    @GetMapping("/show-all-sub-services-by-name/{name}")
    public List<SubServiceDTO> findSubServicesByServiceName(@PathVariable String name) {
        return subServiceService.findSubServiceDTOByServiceName(name);
    }

//    @GetMapping("/show-client-wallet/{email}/{password}")
//    public WalletDTO findClientWalletByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
//        return walletService.findClientWalletByEmailAndPassword(email, password).get();
//    }

    @GetMapping("/show-client-wallet")
    public WalletDTO findClientWallet(){
        return walletService.findUserWallet().get();
    }

    @PutMapping("/pay-by-wallet/{orderId}/{clientId}")
    public ResponseEntity<String> payByWallet(@PathVariable Long orderId, @PathVariable Long clientId) {
        clientService.payByWallet(orderId, clientId);
        return ResponseEntity.ok().body("Payment operation successfully done.");
    }

    @PostMapping("/create-score")
    public ResponseEntity<String> createScore(@RequestBody ScoreDTO scoreDTO) {
        clientService.createScore(scoreDTO);
        return ResponseEntity.ok().body("Score has been submitted successfully.");
    }

    @GetMapping("/payment")
    public ModelAndView showRegister(Model model) {
        CardDTO card = new CardDTO();
        setupCaptcha(card);
        model.addAttribute("card", card);
        return new ModelAndView("payment.html");
    }

    @PostMapping("/payment/pay")
    public String pay(@ModelAttribute("card") CardDTO card, Model model) {
        String page = "";
        if (card.getCaptcha().equals(card.getHidden())) {
            clientService.payByCard(card);
            page = "redirect:all";
        } else {
            setupCaptcha(card);
            return "payment";
        }
        return page;
    }

    private void setupCaptcha(CardDTO card) {
        Captcha captcha = CaptchaUtils.createCaptcha(200, 50);
        card.setHidden(captcha.getAnswer());
        card.setCaptcha("");
        card.setImage(CaptchaUtils.encodeBase64(captcha));
    }


}
