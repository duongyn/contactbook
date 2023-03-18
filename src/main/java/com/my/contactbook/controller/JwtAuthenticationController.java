package com.my.contactbook.controller;

import com.my.contactbook.entity.UserEntity;
import com.my.contactbook.jwt.JwtRequest;
import com.my.contactbook.jwt.JwtResponse;
import com.my.contactbook.jwt.JwtTokenUtil;
import com.my.contactbook.service.JwtUserDetailsService;
import com.my.contactbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest account) throws Exception {
        authenticate(account.getUsername(), account.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(account.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserEntity user = userService.findByUsername(username);
            if (user.getStatus() == UserEntity.EStatus.DISABLE || user.isDeleted()) {
                throw new DisabledException("Cannot Login. User is disable");
            }
        } catch (DisabledException e) {
            throw new Exception("Cannot Login. User is disable", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
