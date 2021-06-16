package com.example.p2pchat.services;

import com.example.p2pchat.controllers.ReceiveMessageDTO;
import com.example.p2pchat.dtos.PublicMessageDTO;
import com.example.p2pchat.dtos.TransportMessageDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface MessageService {

  void setUsername(String username);

  List<PublicMessageDTO> getMessages();

  ReceiveMessageDTO saveMessage(String  newMessageText);

  String getUsername();

  void addNewMessage(ReceiveMessageDTO receivedMessage);

  ResponseEntity<?> receiveMessage(TransportMessageDTO receivedMessage);
}
