package com.example.ModelAgency_Spring.Controllers;

import com.example.ModelAgency_Spring.Models.DTOs.SignInDTO;
import com.example.ModelAgency_Spring.Models.User;
import com.example.ModelAgency_Spring.Repository.IAccountRepo;
import com.example.ModelAgency_Spring.Services.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    IAccountRepo accountRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private HttpSession httpSession;

    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestParam("username") String userEmail,
                                                 @RequestParam("password") String password){
        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setEmail(userEmail);
        signInDTO.setPassword(password);
        if(accountService.verify(signInDTO) != "Authentication failed"
                && accountService.verify(signInDTO) != "Invalid credentials"){
            User user = accountRepo.findByEmail(userEmail);
            System.out.println(user);
            return new ResponseEntity<Integer>(user.getUserId(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Invalid Credentials",HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/adminlogin")
    public ResponseEntity<?> adminLoginAccount(@RequestParam("username") String userEmail,
                                          @RequestParam("password") String password){

        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setEmail(userEmail);
        signInDTO.setPassword(password);
        if(accountService.verify(signInDTO) != "Authentication failed"
                && accountService.verify(signInDTO) != "Invalid credentials"){
            return ResponseEntity.ok("Logged in successfully");
        }
        else{
            return new ResponseEntity<>("Invalid Credentials",HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/register")
    public String registerUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone) {

        try {
            // Validate passwords match
            if (!password.equals(confirmPassword)) {
                throw new RuntimeException("Passwords do not match");
            }

            // Create new user
            User user = new User();

            // Set user properties
            user.setName(firstName + " " + lastName);
            user.setEmail(email);
            user.setPassword(encoder.encode(password)); // Hash password
            user.setAddress(address);
            user.setPhone(phone);
            user.setRole("client");
            user.setCreatedOn(LocalDateTime.now());

            // Save user
            accountRepo.save(user);

            return "redirect:/login?registered=true";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/register?error=" + e.getMessage();
        }
    }

   // @PostMapping("/register")
    //public User registerAccount(){
//        User user = new User();
//        user.setName("new user");
//        user.setEmail("newu@gmail.com");
//        user.setPassword("New@123");
//        user.setPhone("43235");
//        user.setAddress("lhr");
//        user.setRole("client");
//        user.setCreatedOn(LocalDateTime.parse("2024-12-12T00:00:00")); // Use ISO-8601 format

        //return accountService.register(user);
        //return null;
    //}
}
