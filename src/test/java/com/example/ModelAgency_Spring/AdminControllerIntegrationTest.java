package com.example.ModelAgency_Spring;

import com.example.ModelAgency_Spring.Models.HireRecord;
import com.example.ModelAgency_Spring.Models.ModelApplication;
import com.example.ModelAgency_Spring.Repository.IHireRecordRepo;
import com.example.ModelAgency_Spring.Repository.IModelApplicationRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IHireRecordRepo hireRecordRepo;

    @Autowired
    private IModelApplicationRepo modelApplicationRepo;

    @BeforeEach
    public void setUp() {
        // Seed the database with sample data for testing
        HireRecord hireRecord = new HireRecord();
        hireRecord.setState("PENDING");
        hireRecordRepo.save(hireRecord);

        ModelApplication modelApplication = new ModelApplication();
        modelApplication.setState("PENDING");
        modelApplicationRepo.save(modelApplication);
    }

    @Test
    public void testGetNotifications() throws Exception {
        mockMvc.perform(get("/admin/notifications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1)) // Check the number of records returned
                .andExpect(jsonPath("$[0].state").value("PENDING")); // Validate data
    }

    @Test
    public void testGetModelApplications() throws Exception {
        mockMvc.perform(get("/admin/applications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1)) // Check the number of records returned
                .andExpect(jsonPath("$[0].state").value("PENDING")); // Validate data
    }
}

// its the admin controller file, where admin will admin will control.
