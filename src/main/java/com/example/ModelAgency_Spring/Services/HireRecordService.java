package com.example.ModelAgency_Spring.Services;

import com.example.ModelAgency_Spring.Models.HireRecord;
import com.example.ModelAgency_Spring.Repository.IHireRecordRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HireRecordService {

    @Autowired
    IHireRecordRepo hireRecordRepo;

    public HireRecord findByhireId(Integer id) {
        return hireRecordRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("HireRecord not found for id: " + id));
    }


    public HireRecord saveHiringRequest(HireRecord hireRecord){
        return hireRecordRepo.save(hireRecord);
    }

    public List<HireRecord> getPendingRequests(String state){
        return hireRecordRepo.findByState(state);
    }

    public List<HireRecord> getAllRequestsByClientId(Integer clientId) {
        return hireRecordRepo.findByClient_UserId(clientId);
    }

    public List<HireRecord> getAllRequests() {
        return hireRecordRepo.findAll();
    }
}
