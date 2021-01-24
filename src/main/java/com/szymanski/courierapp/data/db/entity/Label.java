package com.szymanski.courierapp.data.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "stickers")
@Immutable
public class Label {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String receiver;

  private String target;

  private String size;

  public String getReceiver() {
    return receiver;
  }

  public String getTarget() {
    return target;
  }

  public String getSize() {
    return size;
  }

  public Long getId() {
    return id;
  }

}
