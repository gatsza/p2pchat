package com.example.p2pchat.controllers;

import com.example.p2pchat.dtos.MessageClientDTO;
import com.example.p2pchat.dtos.TransportMessageDTO;
import com.example.p2pchat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class ClientController {

  private MessageService messageService;
  private RestTemplate restTemplate;

  @Autowired
  public ClientController(MessageService messageService) {
    this.messageService = messageService;
    this.restTemplate = new RestTemplate();
  }

  @GetMapping({"/", "/login"})
  public String showLogin() {
    return "index";
  }

  @PostMapping("/login")
  public String login(String username) {
    messageService.setUsername(username);
    return "redirect:message";
  }

  @GetMapping("/message")
  public String showMessages(Model model) {
    String username = messageService.getUsername();

    if (username == null) {
      return "redirect:login";
    }
    model.addAttribute("username", username);
    model.addAttribute("messages", messageService.getMessages());
    return "message";
  }

  @PostMapping("/message")
  public String sendMessage(@ModelAttribute(name = "new-message") String newMessageText,
      @RequestParam String username) {
    MessageClientDTO client = new MessageClientDTO( System.getenv("CHAT_APP_UNIQUE_ID"));
    ReceiveMessageDTO message = messageService.saveMessage(newMessageText);
    TransportMessageDTO newMessage = new TransportMessageDTO(message, client);

    HttpEntity<TransportMessageDTO> httpEntity = new HttpEntity<>(newMessage);
    String address = System.getenv("CHAT_APP_PEER_ADDRESS");
    restTemplate.exchange(address + "/api/message/receive",
        HttpMethod.POST, httpEntity, TransportMessageDTO.class);
    return "redirect:message";
  }

  @PostMapping("/api/message/receive")
  public ResponseEntity<?> getNewMessageFromColony(
      @RequestBody TransportMessageDTO receivedMessage
  ) {
    return messageService.receiveMessage(receivedMessage);
  }
}
