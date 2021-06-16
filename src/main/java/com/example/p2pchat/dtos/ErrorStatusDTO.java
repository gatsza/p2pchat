package com.example.p2pchat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorStatusDTO {

  private String status;
  private String message;

}
