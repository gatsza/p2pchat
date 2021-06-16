package com.example.p2pchat.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveMessageDTO {
  Long id;
  String username;
  String text;
  Long timestamp;
}
