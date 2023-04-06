package com.example.maria.Controller;

import com.example.maria.Model.User;
import com.example.maria.Service.AuthenticationService;
import com.example.maria.Repository.UserRepository;
import com.example.maria.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;

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
    public String processCreate(@Validated RegisterRequest registerRequest,
                                BindingResult result,
                                Model model){

        if (result.hasErrors()) {
            System.out.println(" Signup Error //////// : " + result.getAllErrors().toString());
            return "redirect:/api/v1/login/createAccount";
        }
        User user = service.register(registerRequest);
        model.addAttribute("User", user) ;
        return "redirect:/api/v1/login/";
    }

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
//        return ResponseEntity.ok(service.register(request));
//    }
}
