package com.example.ModelAgency_Spring.Repository;
import com.example.ModelAgency_Spring.Models.ModelApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IModelApplicationRepo extends JpaRepository<ModelApplication,Integer>{
    ModelApplication findByApplicationId(Integer applicationId);
    List<ModelApplication> findByState(String state);
}
