package com.example.ModelAgency_Spring.Services;

import com.example.ModelAgency_Spring.Models.DTOs.SignInDTO;
import com.example.ModelAgency_Spring.Models.User;
import com.example.ModelAgency_Spring.Repository.IAccountRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private IAccountRepo accountRepo;

    @Autowired
    HttpSession httpSession;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private AuthenticationManager authenticationManager;

    public User register(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        accountRepo.save(user);
        httpSession.setAttribute("userId",user.getUserId());

        return user;
    }

    public String verify(SignInDTO uSign) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(uSign.getEmail(), uSign.getPassword())
            );

            if (authentication.isAuthenticated()) {
                User user = accountRepo.findByEmail(uSign.getEmail());
                httpSession.setAttribute("userId",user.getUserId());
                System.out.println("User Id " + httpSession.getAttribute("userId"));
                // Generate and return JWT token
                return jwtService.generateToken(uSign.getEmail());
            }

            return "Authentication failed"; // Rare case, as exceptions should already be thrown
        } catch (BadCredentialsException ex) {
            return "Invalid credentials";
        } catch (Exception ex) {
            return "An error occurred: " + ex.getMessage();
        }
    }

}
