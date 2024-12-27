package com.example.ModelAgency_Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ModelAgencySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModelAgencySpringApplication.class, args);

	}


	@PostMapping("/SubmitApplication")
	public void ModelApplication(){

	}
}
