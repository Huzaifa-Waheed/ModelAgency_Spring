package com.example.ModelAgency_Spring.Repository;

import com.example.ModelAgency_Spring.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepo extends JpaRepository<User,Integer> {

    User findByEmail(String userEmail);
}
