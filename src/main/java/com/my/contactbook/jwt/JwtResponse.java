package com.my.contactbook.jwt;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class JwtResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  private String username;

  private String jwtToken;

  public JwtResponse(String jwtToken) {
    super();
    this.jwtToken = jwtToken;
  }


}
