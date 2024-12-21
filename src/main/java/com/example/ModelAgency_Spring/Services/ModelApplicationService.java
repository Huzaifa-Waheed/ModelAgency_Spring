package com.example.ModelAgency_Spring.Services;

import com.example.ModelAgency_Spring.Models.ModelApplication;
import com.example.ModelAgency_Spring.Repository.IModelApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelApplicationService {
    @Autowired
    private IModelApplicationRepo modelRepo;

    public ModelApplication saveApplication(ModelApplication modelAppl){
        return modelRepo.save(modelAppl);
    }
}
