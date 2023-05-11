package com.my.contactbook.jwt;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;

    private List<String> roles;

    private String jwtToken;

    public JwtResponse(String jwtToken) {
        super();
        this.jwtToken = jwtToken;
    }

    public JwtResponse(String username, List<String> roles, String jwtToken) {
        super();
        this.jwtToken = jwtToken;
    }
}
