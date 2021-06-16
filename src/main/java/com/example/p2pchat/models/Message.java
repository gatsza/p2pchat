package com.example.p2pchat.models;

import com.example.p2pchat.controllers.ReceiveMessageDTO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {

  @Id
  private Long id;

  private String username;
  private String text;
  private Long timestamp;

  public Message(ReceiveMessageDTO newMessage){
    this.id = newMessage.getId();
    this.text = newMessage.getText();
    this.username = newMessage.getUsername();
    this.timestamp = newMessage.getTimestamp();
  }
}
