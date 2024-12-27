package com.example.ModelAgency_Spring.Services;

import com.example.ModelAgency_Spring.Models.Model;
import com.example.ModelAgency_Spring.Models.ModelApplication;
import com.example.ModelAgency_Spring.Repository.IModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {

    @Autowired
    private IModelRepo modelRepo;

    public Model saveModel(Model model){
        return modelRepo.save(model);
    }

    public Model findByModelId(Integer id){
        return modelRepo.findById(id).orElseThrow(() -> new RuntimeException("Model not found"));
    }

    public void deleteModelById(Integer id){
        modelRepo.deleteById(id);
    }

    public List<Model> getAllModels() {
        return  modelRepo.findAll();
    }
}
