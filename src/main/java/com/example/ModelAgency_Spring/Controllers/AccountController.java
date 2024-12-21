package com.example.ModelAgency_Spring.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestParam("username") String userName,
                                                 @RequestParam("password") String password){
        return new ResponseEntity<>("Logged in successfully", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(){
        return new ResponseEntity<>("Sign Up successfully",HttpStatus.CREATED);
    }
}
