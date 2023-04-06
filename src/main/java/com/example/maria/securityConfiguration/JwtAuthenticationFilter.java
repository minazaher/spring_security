package com.example.maria.securityConfiguration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

       // https//:www.asdasdasd.com?authrouization=Bearer ?user=sdadsads?

        final String authHeader = request.getHeader("Authorization");
        final String JWT;
        final String userEmail;

//        the header must start with bearer
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
//        extracting token
        //jwt = asdasdweerwRW
        JWT = authHeader.substring(7);

//        the email is our "username" in this case
        userEmail = jwtService.extractUsername(JWT);

//        checking if the user is not authenticated yet because if it is authenticated we won't need to do this process
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
//           retrieving the user from our database
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//             check the function impl
            if (jwtService.isTokenValid(JWT,userDetails)){
//                create an authentication token to be used further in this session
                UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }
    }
    private Boolean isAuthValid(String authHeader){
        return authHeader == null ||!authHeader.startsWith("Bearer ");
    }
}
