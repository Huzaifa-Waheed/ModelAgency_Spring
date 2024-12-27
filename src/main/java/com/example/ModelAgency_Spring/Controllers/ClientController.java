package com.example.ModelAgency_Spring.Controllers;

import com.example.ModelAgency_Spring.Models.HireRecord;
import com.example.ModelAgency_Spring.Models.DTOs.HireRecordDTO;
import com.example.ModelAgency_Spring.Models.Model;
import com.example.ModelAgency_Spring.Models.User;
import com.example.ModelAgency_Spring.Repository.IAccountRepo;
import com.example.ModelAgency_Spring.Repository.IHireRecordRepo;
import com.example.ModelAgency_Spring.Services.HireRecordService;
import com.example.ModelAgency_Spring.Services.ModelService;
import com.example.ModelAgency_Spring.Utility.Enums.States;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.FileStore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {


    @Autowired
    ModelService modelService;

    @Autowired
    HireRecordService hireRecordService;

    @Autowired
    IHireRecordRepo hireRecordRepo;

    @Autowired
    HttpSession httpSession;

    @Autowired
    private IAccountRepo userRepository;

//    @PostMapping("/hiringmodel")
//    public ResponseEntity<?> createHireRecord(@RequestPart("hireRecordData") String hireRecordData) throws IOException {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, String> formData = objectMapper.readValue(hireRecordData, Map.class);
//            System.out.println(formData);
//            // Fetch related entities using IDs
//            int modelId;
//            String modelIdStr = formData.get("modelId");
//            if (modelIdStr != null) {
//                modelId = Integer.parseInt(modelIdStr);
//            } else {
//                modelId = Integer.parseInt(formData.get("modelId"));
//            }
//
//            System.out.println("model Id "+modelId);
//            Model model = modelService.findByModelId(modelId);
//            System.out.println(model);
//            System.out.println(formData.get("clientId"));
//            int userId = Integer.parseInt(formData.get("clientId"));
//
//            System.out.println(userId);
//            User client = userRepository.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//            System.out.println(client);
//            // Create HireRecord
//            HireRecord hireRecord = new HireRecord();
//            hireRecord.setModel(model);
//            hireRecord.setClient(client);
//            hireRecord.setAmount(Double.parseDouble(formData.get("amount")));
//            hireRecord.setState(States.PENDING.toString());
//            hireRecord.setDescription(formData.get("description"));
//            // Use the correct formatter for a date-only string (YYYY-MM-DD)
//            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
//            LocalDate requestedDate = LocalDate.parse(formData.get("requestedDate"), formatter);
//
//            // Convert LocalDate to LocalDateTime (optional, depends on your requirements)
//            hireRecord.setRequestedDate(requestedDate.atStartOfDay());
//            hireRecord.setDate(LocalDateTime.now());
//            hireRecord.setStateDate(LocalDateTime.now());
//
//            // Save HireRecord
//            HireRecord savedRecord = hireRecordService.saveHiringRequest(hireRecord);
//
//            return ResponseEntity.ok("Hire request applied successfully");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }


    @PostMapping("/hiringmodel")
    public ResponseEntity<?> createHireRecord(
            @RequestParam("modelId") Integer modelId,
            @RequestParam("clientId") Integer clientId,
            @RequestParam("amount") Double amount,
            @RequestParam("description") String description,
            @RequestParam("requestedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate requestedDate) {

        try {
            // Get model and client
            Model model = modelService.findByModelId(modelId);
            User client = userRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Create and populate HireRecord
            HireRecord hireRecord = new HireRecord();
            hireRecord.setModel(model);
            hireRecord.setClient(client);
            hireRecord.setState(States.PENDING.toString());
            hireRecord.setDescription(description);
            hireRecord.setAmount(amount);
            hireRecord.setRequestedDate(requestedDate.atStartOfDay());
            hireRecord.setDate(LocalDateTime.now());
            hireRecord.setStateDate(LocalDateTime.now());

            // Save record
            hireRecordService.saveHiringRequest(hireRecord);

            return ResponseEntity.ok("Saved");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/clientrequest/{id}")
    public ResponseEntity<?> clientRequest(@PathVariable("id") Integer id){
        try{

            List<HireRecord> hireRecords = hireRecordService.getAllRequestsByClientId(id);
            return ResponseEntity.ok(hireRecords);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptHiring(@PathVariable("id") Integer hireId){
        try{
            HireRecord hireRecord = hireRecordService.findByhireId(hireId);

            if(hireRecord != null){
                hireRecord.setState(States.ACCEPTED.toString());
                hireRecord.setStateDate(LocalDateTime.now());
                hireRecordService.saveHiringRequest(hireRecord);
                return ResponseEntity.ok("Hire request applied successfully");
            }
            return new ResponseEntity<>("request not found",HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving application: " + e.getMessage());
        }
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectHiring(@PathVariable("id") Integer hireId){
        try{
            HireRecord hireRecord = hireRecordService.findByhireId(hireId);

            if(hireRecord != null){
                hireRecord.setState(States.ACCEPTED.toString());
                hireRecord.setStateDate(LocalDateTime.now());
                hireRecordService.saveHiringRequest(hireRecord);
                return ResponseEntity.ok("Hire request applied successfully");
            }
            return new ResponseEntity<>("request not found",HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving application: " + e.getMessage());
        }
    }
}
