package com.jwt.controller;

import com.jwt.model.AuthRequest;
import com.jwt.service.RegisterService;
import com.jwt.service.UserService;
import com.jwt.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping("/")
    public String hello(){
        return "Hello Duniya!!";
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RegisterService registerService;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
            final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
            String token = JWTUtil.generateToke(userDetails);
            return token;

        }catch (BadCredentialsException e){
            throw e;
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody AuthRequest request){
        final String resp = registerService.createUser(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/getUserDetails/{user}")
    public ResponseEntity<UserDetails> createUser(@PathVariable String user){
        UserDetails userDetails =userService.loadUserByUsername(user);
        return ResponseEntity.ok(userDetails);
    }

    @GetMapping("/details")
    public String details(){
        return "Welcome Varun.";
    }

    @GetMapping("/refreshToken")
    public String refreshToken(@RequestHeader("Authorization") String auth){
        String token = auth.substring(7);
        String refreshToken=  null;
        String username = null;
        if(null!=token){
            username = JWTUtil.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            refreshToken = JWTUtil.generateToke(userDetails);
        }
        return refreshToken;
    }
}
