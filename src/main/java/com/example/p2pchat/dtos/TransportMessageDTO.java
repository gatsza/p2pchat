package com.example.p2pchat.dtos;

import com.example.p2pchat.controllers.ReceiveMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransportMessageDTO {

  private ReceiveMessageDTO message;
  private MessageClientDTO client;

}
