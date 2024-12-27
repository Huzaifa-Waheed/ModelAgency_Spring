package com.example.ModelAgency_Spring.Controllers;

import com.example.ModelAgency_Spring.Models.DTOs.ModelApplicationDTO;
import com.example.ModelAgency_Spring.Models.Model;
import com.example.ModelAgency_Spring.Models.ModelApplication;
import com.example.ModelAgency_Spring.Services.ModelApplicationService;
import com.example.ModelAgency_Spring.Services.ModelService;
import com.example.ModelAgency_Spring.Services.SendinBlueService;
import com.example.ModelAgency_Spring.Utility.Enums.States;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.example.ModelAgency_Spring.Utility.StaticDetails.UPLOAD_DIR;


@RestController
@RequestMapping("/applications")
public class ModelApplicationController {

    @Autowired
    ModelApplicationService modelService;

    @Autowired
    ModelService modService;
    @Autowired
    SendinBlueService sendinBlueService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitApplication(@RequestPart("restData") String modApplDTO,
                                              @RequestPart("imgUrl1") MultipartFile imgUrl1,
                                              @RequestPart("imgUrl2") MultipartFile imgUrl2,
                                              @RequestPart("imgUrl3") MultipartFile imgUrl3
    )throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> formData = objectMapper.readValue(modApplDTO, Map.class);
        // Use this map to construct your model object and you are donzo 😁

        String imageUrl1 = saveImage(imgUrl1);
        String imageUrl2 = saveImage(imgUrl2);
        String imageUrl3 = saveImage(imgUrl3);

        ModelApplication modelApplication = new ModelApplication();

        setModelApplicationData(modelApplication,formData); // set the values

        modelApplication.setImgUrl1(imageUrl1);
        modelApplication.setImgUrl2(imageUrl2);
        modelApplication.setImgUrl3(imageUrl3);

        try {
            modelService.saveApplication(modelApplication);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving application: " + e.getMessage());
        }

        return ResponseEntity.ok("form submitted");
    }

    private void setModelApplicationData(ModelApplication modelApplication,Map<String, String> formData){

        try{

            modelApplication.setName(formData.get("name"));
            modelApplication.setEmail(formData.get("email"));
            modelApplication.setPhone(formData.get("phone"));
            modelApplication.setLocation(formData.get("location"));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date dateofBirth = formatter.parse(formData.get("dob"));

            modelApplication.setDob(dateofBirth);
            modelApplication.setGender(formData.get("gender"));
            modelApplication.setWeight(Double.parseDouble(formData.get("weight")));
            modelApplication.setHeight(Double.parseDouble(formData.get("height")));
            modelApplication.setHair(formData.get("hair"));
            modelApplication.setHips(Double.parseDouble(formData.get("hips")));
            modelApplication.setEyeColor(formData.get("eyeColor"));
            modelApplication.setDescription(formData.get("description"));
            modelApplication.setRate(Double.parseDouble(formData.get("rate")));
            modelApplication.setCategory(formData.get("category"));
            modelApplication.setAge((LocalDateTime.now().getYear()- dateofBirth.getYear() - 2000));
            modelApplication.setState(States.PENDING.toString());
            modelApplication.setStateDate(LocalDateTime.now());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // Get the original filename
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String randomGuid = UUID.randomUUID().toString().substring(0,14);
        fileName = randomGuid + fileName;
        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Create a Path to the file
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        String fileUrl = filePath.toString();

        return fileUrl;
    }

    @PostMapping("/submit-form")
    public String handleSubmit(@RequestParam String username, @RequestParam String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        return "Form submitted successfully!";
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptModelApplication(@PathVariable("id") Integer modelApplId){
        try{
            ModelApplication ma = modelService.findById(modelApplId);
            if (ma != null){
                Model model = new Model();

                createModel(model,ma);

                modService.saveModel(model);

                ma.setState(States.ACCEPTED.toString());
                ma.setStateDate(LocalDateTime.now());

                modelService.changeApplicationState(ma);
                sendinBlueService.sendEmail(model.getEmail(), "Application Status", "ACCEPTED!");
                return new ResponseEntity<>("Hire request applied successfully",HttpStatus.OK);
            }
            return new ResponseEntity<>("Model application not found",HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectApplication(@PathVariable("id") Integer modelApplId){
        try{
            ModelApplication modelApplication = modelService.findById(modelApplId);
            if(modelApplication != null){
                modelApplication.setState(States.REJECTED.toString());
                modelApplication.setStateDate(LocalDateTime.now());

                modelService.changeApplicationState(modelApplication);
                return new ResponseEntity<>("Hire request rejected",HttpStatus.OK);
            }

            return new ResponseEntity<>("Model application not found",HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    private void createModel(Model model, ModelApplication ma){
        model.setName(ma.getName());
        model.setEmail(ma.getEmail());
        model.setPhone(ma.getPhone());
        model.setLocation(ma.getLocation());
        model.setDob(ma.getDob());
        model.setGender(ma.getGender());
        model.setWeight(ma.getWeight());
        model.setHeight(ma.getHeight());
        model.setHair(ma.getHair());
        model.setHips(ma.getHips());
        model.setEyeColor(ma.getEyeColor());
        model.setDescription(ma.getDescription());
        model.setRate(ma.getRate());
        model.setCategory(ma.getCategory());
        model.setAge(ma.getAge());
        model.setImgUrl1(ma.getImgUrl1());
        model.setImgUrl2(ma.getImgUrl2());
        model.setImgUrl3(ma.getImgUrl3());
        model.setCreatedOn(ma.getStateDate());
        model.setAvailability(true);
    }

}
