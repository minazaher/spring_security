package com.example.maria.auth;

import com.example.maria.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @RequestMapping("/")
    public String sayHi(){
        return "login.html";
    }

//    @RequestMapping(value = "/",     method= RequestMethod.POST)
//    public String register(@RequestBody RegisterRequest request){
//          ResponseEntity.ok(service.register(request));
//          return "login.html";
//    }
//
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody User user){
        System.out.println(service.authenticate(user).token);
        return ResponseEntity.ok(service.authenticate(user));
    }
    @RequestMapping("/home")
    public String sai(){
        return "home.html";
    }

    }

