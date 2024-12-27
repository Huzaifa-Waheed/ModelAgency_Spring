package com.example.ModelAgency_Spring;

import com.example.ModelAgency_Spring.Models.HireRecord;
import com.example.ModelAgency_Spring.Models.Model;
import com.example.ModelAgency_Spring.Repository.IModelRepo;
import com.example.ModelAgency_Spring.Services.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ModelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelService modelService;

    @Autowired
    private IModelRepo modelRepo; // Replace with your actual repository interface

    @BeforeEach
    public void setUp() {
        // Seed the database with sample data for testing
        Model model1 = new Model();
        model1.setName("Test Model 1");
        modelRepo.save(model1);

        Model model2 = new Model();
        model2.setName("Test Model 2");
        modelRepo.save(model2);
    }

    @Test
    public void testDeleteModel() throws Exception {
        // Fetch an existing model's ID for testing
        Model model = modelRepo.findAll().get(0);

        mockMvc.perform(delete("/model/delete/" + model.getModelId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        // Verify the model is deleted
        assertFalse(modelRepo.findById(model.getModelId()).isPresent());
    }

    @Test
    public void testGetAllModels() throws Exception {
        mockMvc.perform(get("/model/show")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)); // Verify two models exist
    }

    @Test
    public void testGetSingleModel() throws Exception {
        // Fetch an existing model's ID for testing
        Model model = modelRepo.findAll().get(0);

        mockMvc.perform(get("/model/getmodel/" + model.getModelId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(model.getModelId()))
                .andExpect(jsonPath("$.name").value(model.getName()));
    }

    @Test
    public void testDeleteModelNotFound() throws Exception {
        mockMvc.perform(delete("/model/delete/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error in deleting model")));
    }
}

