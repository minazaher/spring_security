package com.example.maria.Service;

import com.example.maria.securityConfiguration.JwtService;
import com.example.maria.dto.AuthenticationRequest;
import com.example.maria.dto.AuthenticationResponse;
import com.example.maria.dto.RegisterRequest;
import com.example.maria.Model.Role;
import com.example.maria.Model.User;
import com.example.maria.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterRequest request) {
        User user = User.builder().
                firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())).
                role(Role.USER).
                build();
        repo.save(user);
        var jwtToken  = jwtService.generateToken(user);
        return user;
    }

    public AuthenticationResponse register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        repo.save(user);
        String jwtToken  = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));
        User user = repo.findUserByEmail(request.getEmail());
        String jwtToken  = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public AuthenticationResponse authenticate(User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()
        ));
        try
        {
            User newuser = repo.findUserByEmail(user.getEmail());
            String jwtToken  = jwtService.generateToken(newuser);
            return AuthenticationResponse.builder().token(jwtToken).build();
        }catch (Exception NoSuchElementException){
            return new AuthenticationResponse("NO USER") ;
        }
    }

    public boolean login(User user){
        User u = repo.findUserByEmail(user.getEmail());
        return u != null;
    }

}
