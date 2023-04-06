package com.example.maria.Controller;

import com.example.maria.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/signup")
public class registrationController {
    private final AuthenticationService service;


}
