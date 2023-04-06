package com.example.maria.auth;

import com.example.maria.user.User;
import com.example.maria.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class loginController {
    private final AuthenticationService service;

    private final UserRepository userRepository;
    @GetMapping("/")
    public String loginForm(Model model){
        model.addAttribute("user", new User());
        return "loginNew";
    }

    @PostMapping("/processLogin")
    public String processLogin(User user , Model model){
        model.addAttribute("user", userRepository.findUserByEmail(user.getEmail()));
        if (service.login(user)){
            return "index.html";
        }
            return "loginNew.html";
    }

    @GetMapping("/createAccount")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return "registerNew.html";
    }

    @PostMapping("/accountCreated")
    public String processCreate(User user, Model model){
        model.addAttribute("User", user) ;
        service.register(user);
        return "redirect:/api/v1/login/";
    }

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
//        return ResponseEntity.ok(service.register(request));
//    }
}
