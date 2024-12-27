package com.example.ModelAgency_Spring.Repository;

import com.example.ModelAgency_Spring.Models.HireRecord;
import com.example.ModelAgency_Spring.Models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModelRepo extends JpaRepository<Model, Integer> {

}