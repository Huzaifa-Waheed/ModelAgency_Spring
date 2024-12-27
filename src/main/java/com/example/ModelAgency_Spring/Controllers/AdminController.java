package com.example.ModelAgency_Spring.Controllers;

import com.example.ModelAgency_Spring.Services.HireRecordService;
import com.example.ModelAgency_Spring.Services.ModelApplicationService;
import com.example.ModelAgency_Spring.Utility.Enums.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    HireRecordService hireRecordService;

    @Autowired
    ModelApplicationService modelApplicationService;

    @GetMapping("/notifications")
    public ResponseEntity<?> getNotifications(){
        return new ResponseEntity<>(hireRecordService.getPendingRequests(States.PENDING.toString()), HttpStatus.OK);
    }

    @GetMapping("/applications")
    public ResponseEntity<?> getModelApplications(){
        return new ResponseEntity<>(modelApplicationService.getPendingApplications(States.PENDING.toString()),HttpStatus.OK);
    }

}
