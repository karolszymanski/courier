package com.szymanski.courierapp.model.ws;

import java.time.LocalDateTime;

public class Notification extends Message {

  private String sender;

  private LocalDateTime timestamp;

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }


  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

}
