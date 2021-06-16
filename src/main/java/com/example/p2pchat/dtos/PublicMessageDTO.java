package com.example.p2pchat.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublicMessageDTO {

  private String username;
  private String text;
  private Long timestamp;

}
