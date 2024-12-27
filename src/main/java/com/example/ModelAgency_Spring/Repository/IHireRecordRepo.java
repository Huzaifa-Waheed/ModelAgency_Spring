package com.example.ModelAgency_Spring.Repository;

import com.example.ModelAgency_Spring.Models.HireRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IHireRecordRepo extends JpaRepository<HireRecord, Integer> {

    List<HireRecord> findByState(String state);
    List<HireRecord> findByClient_UserId(Integer clientId);
}
