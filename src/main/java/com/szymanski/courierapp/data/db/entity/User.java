package com.szymanski.courierapp.data.db.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "users")
@Immutable
public class User {

  @Id
  private Long id;

  private String name;

  private String surname;

  private String login;

  private String email;

  private String password;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getLogin() {
    return login;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

}
