package com.my.contactbook.exception;

import io.jsonwebtoken.JwtException;

public class JwtExpiredHandleException extends JwtException {

    public JwtExpiredHandleException(String message) {
        super(message);
    }
}
