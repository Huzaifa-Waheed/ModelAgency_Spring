package com.example.ModelAgency_Spring.Services;

import com.example.ModelAgency_Spring.Models.ModelApplication;
import com.example.ModelAgency_Spring.Repository.IModelApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelApplicationService {
    @Autowired
    private IModelApplicationRepo modelApplRepo;

    public ModelApplication saveApplication(ModelApplication modelAppl){
        return modelApplRepo.save(modelAppl);
    }

    public List<ModelApplication> getAll() {
        return modelApplRepo.findAll();
    }

    public List<ModelApplication> getPendingApplications(String state){
        return modelApplRepo.findByState(state);
    }

    public ModelApplication findById(Integer applicationId){
        return modelApplRepo.findByApplicationId(applicationId);
    }

    public ModelApplication changeApplicationState(ModelApplication modelApplication){
        return modelApplRepo.save(modelApplication);
    }
}
