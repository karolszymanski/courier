package com.szymanski.courierapp.model;

import org.springframework.hateoas.RepresentationModel;

public class LabelResponse extends RepresentationModel<LabelResponse> {

  private Long id;

  private String receiver;

  private String target;

  private String size;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

}
