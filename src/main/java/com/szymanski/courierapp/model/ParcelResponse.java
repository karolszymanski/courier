package com.szymanski.courierapp.model;

import org.springframework.hateoas.RepresentationModel;

public class ParcelResponse extends RepresentationModel<ParcelResponse> {

  private Long id;

  private Long labelId;

  private String status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getLabelId() {
    return labelId;
  }

  public void setLabelId(Long labelId) {
    this.labelId = labelId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
