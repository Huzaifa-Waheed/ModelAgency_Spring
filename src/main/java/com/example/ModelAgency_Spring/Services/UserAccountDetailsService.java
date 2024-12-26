package com.example.ModelAgency_Spring.Services;

import com.example.ModelAgency_Spring.Models.User;
import com.example.ModelAgency_Spring.Models.UserPrincipal;
import com.example.ModelAgency_Spring.Repository.IAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAccountDetailsService implements UserDetailsService {

    @Autowired
    IAccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = accountRepo.findByEmail(userEmail);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}
