package com.example.ModelAgency_Spring;

import com.example.ModelAgency_Spring.Models.HireRecord;
import com.example.ModelAgency_Spring.Models.Model;
import com.example.ModelAgency_Spring.Repository.IAccountRepo;
import com.example.ModelAgency_Spring.Repository.IHireRecordRepo;
import com.example.ModelAgency_Spring.Services.HireRecordService;
import com.example.ModelAgency_Spring.Services.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HireRecordService hireRecordService;

    @Autowired
    private IHireRecordRepo hireRecordRepo;

    @Autowired
    private ModelService modelService;

    @Autowired
    private IAccountRepo userRepository;

    @BeforeEach
    public void setUp() {
        // Seed the database with a sample hire record and client
        // Assuming you have a method to save hire records and a user model to simulate the client.
        Model model = new Model();
        model.setName("Test Model");
        modelService.saveModel(model);

        HireRecord hireRecord = new HireRecord();
        hireRecord.setId(1);  // Set this clientId according to your logic
        hireRecord.setState("PENDING");
        hireRecordRepo.save(hireRecord);
    }

    @Test
    public void testClientRequest_Success() throws Exception {
        // Assuming a client with ID 1 exists
        Integer clientId = 1;

        mockMvc.perform(get("/client/clientrequest/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) // Expecting a list of hire records
                .andExpect(jsonPath("$[0].clientId").value(clientId));
    }

    @Test
    public void testClientRequest_NoRecords() throws Exception {
        // Assuming no hire records exist for client ID 999
        Integer clientId = 999;

        mockMvc.perform(get("/client/clientrequest/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());  // No hire records for this client
    }

    @Test
    public void testClientRequest_Error() throws Exception {
        // Simulating error (e.g., invalid ID or service issue)
        mockMvc.perform(get("/client/clientrequest/{id}", -1) // Assuming -1 is invalid
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("null")); // Body is null
    }
}

