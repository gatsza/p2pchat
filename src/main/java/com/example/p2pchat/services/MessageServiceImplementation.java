package com.example.p2pchat.services;


import com.example.p2pchat.controllers.ReceiveMessageDTO;
import com.example.p2pchat.dtos.ErrorStatusDTO;
import com.example.p2pchat.dtos.MessageClientDTO;
import com.example.p2pchat.dtos.PublicMessageDTO;
import com.example.p2pchat.dtos.StatusDTO;
import com.example.p2pchat.dtos.TransportMessageDTO;
import com.example.p2pchat.models.Message;
import com.example.p2pchat.repositories.MessageRepository;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImplementation implements MessageService {

  private String username;
  private MessageRepository messageRepository;
  private ModelMapper modelMapper;

  @Autowired
  public MessageServiceImplementation(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
    this.modelMapper = new ModelMapper();
  }

  @Override
  public void setUsername(String username) {
    this.username = username;
  }


  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public void addNewMessage(ReceiveMessageDTO receivedMessage) {
    Message newMessage = modelMapper.map(receivedMessage, Message.class);
    messageRepository.save(newMessage);
  }

  @Override
  public ResponseEntity<?> receiveMessage(TransportMessageDTO receivedMessage) {
    ErrorStatusDTO error = validateMessage(receivedMessage);
    if (error != null) {
      return ResponseEntity.badRequest().body(error);
    }
    addNewMessage(receivedMessage.getMessage());
    return ResponseEntity.ok(new StatusDTO("ok"));
  }

  @Override
  public List<PublicMessageDTO> getMessages() {

    return messageRepository.findAll().stream()
        .map(m -> modelMapper.map(m, PublicMessageDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public ReceiveMessageDTO saveMessage(String newMessageText) {
    Long id = generateRandomId();
    Long time = Instant.now().getEpochSecond();
    Message newMessage = new Message(id, username, newMessageText, time);
    messageRepository.save(newMessage);
    return modelMapper.map(newMessage, ReceiveMessageDTO.class);
  }

  private ErrorStatusDTO validateMessage(TransportMessageDTO receivedMessage) {
    StringBuilder missingfields = new StringBuilder();

    if (receivedMessage == null) {
      missingfields.append("messageBody");
    } else {
      ReceiveMessageDTO messageDTO = receivedMessage.getMessage();
      if (messageDTO == null) {
        missingfields.append("message");
      } else {
        if (messageDTO.getId() == null){
          missingfields.append("message.id");
        }
        if (messageDTO.getText() == null){
          missingfields.append("message.text");
        }
        if (messageDTO.getUsername() == null){
          missingfields.append("message.username");
        }
        if (messageDTO.getTimestamp() == null){
          missingfields.append("message.timestamp");
        }
      }
      MessageClientDTO client = receivedMessage.getClient();
      if(client == null){
        missingfields.append("client");
      }else if(client.getId() == null){
        missingfields.append("client.id");
      }
    }
    if (missingfields.length() == 0) {
      return null;
    }
    return new ErrorStatusDTO("error", "Missing field(s): " + missingfields.toString());
  }

  private long generateRandomId() {
    Random random = new Random();
    long id;
    do {
      id = (long) (1000000 + random.nextInt(9000000));
    } while (messageRepository.existsById(id));
    return id;
  }

}
