package com.example.ModelAgency_Spring.Controllers;

import com.example.ModelAgency_Spring.Models.Model;
import com.example.ModelAgency_Spring.Models.User;
import com.example.ModelAgency_Spring.Repository.IAccountRepo;
import com.example.ModelAgency_Spring.Services.ModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.example.ModelAgency_Spring.Utility.StaticDetails.UPLOAD_DIR;

@RestController
@RequestMapping("/model")
public class ModelController {
    @Autowired
    ModelService modelService;

    @Autowired
    IAccountRepo userRepo;

    @PostMapping("/add")
    public ResponseEntity<?> addModel(@RequestPart("restData") String modelDTO,
                                               @RequestPart("imgUrl1") MultipartFile imgUrl1,
                                               @RequestPart("imgUrl2") MultipartFile imgUrl2,
                                               @RequestPart("imgUrl3") MultipartFile imgUrl3
    )throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> formData = objectMapper.readValue(modelDTO, Map.class);
        System.out.println(formData);

        String imageUrl1 = saveImage(imgUrl1);
        String imageUrl2 = saveImage(imgUrl2);
        String imageUrl3 = saveImage(imgUrl3);
        System.out.println(imageUrl1);
        Model model = new Model();

        setModelData(model,formData);
        model.setImgUrl1(imageUrl1);
        model.setImgUrl2(imageUrl2);
        model.setImgUrl3(imageUrl3);
        try {
            modelService.saveModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving form: " + e.getMessage());
        }

        return  new ResponseEntity<>("Model added successfully", HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> updateModel(@RequestPart("restData") String modelDTO,
                                      @RequestPart("imgUrl1") MultipartFile imgUrl1,
                                      @RequestPart("imgUrl2") MultipartFile imgUrl2,
                                      @RequestPart("imgUrl3") MultipartFile imgUrl3
    )throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> formData = objectMapper.readValue(modelDTO, Map.class);
            int modelId = Integer.parseInt(formData.get("modelId"));
            Model model = modelService.findByModelId(modelId);
            if(model != null){
                String imageUrl1 = saveImage(imgUrl1);
                String imageUrl2 = saveImage(imgUrl2);
                String imageUrl3 = saveImage(imgUrl3);

                setModelData(model,formData);

                model.setImgUrl1(imageUrl1);
                model.setImgUrl2(imageUrl2);
                model.setImgUrl3(imageUrl3);

                modelService.saveModel(model);
                return  new ResponseEntity<>("Model edited successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Model not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving form: " + e.getMessage());
        }

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteModel(@PathVariable("id") Integer modelId){
        try{
            modelService.deleteModelById(modelId);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in deleting model: " + e.getMessage());
        }
        return ResponseEntity.ok("Deleted");
    }

    private void setModelData(Model model, Map<String, String> formData){

        try{

            model.setName(formData.get("name"));
            model.setEmail(formData.get("email"));
            model.setPhone(formData.get("phone"));
            model.setLocation(formData.get("location"));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date dateofBirth = formatter.parse(formData.get("dob"));

            model.setDob(dateofBirth);
            model.setGender(formData.get("gender"));
            model.setWeight(Double.parseDouble(formData.get("weight")));
            model.setHeight(Double.parseDouble(formData.get("height")));
            model.setHair(formData.get("hair"));
            int age = LocalDateTime.now().getYear() - dateofBirth.getYear() - 2000;
            model.setAge(age);
            model.setHips(Double.parseDouble(formData.get("hips")));
            model.setEyeColor(formData.get("eyeColor"));
            model.setDescription(formData.get("description"));
            model.setRate(Double.parseDouble(formData.get("rate")));
            model.setCategory(formData.get("category"));
            model.setAvailability(true);
            model.setCreatedOn(LocalDateTime.now());
            User user = userRepo.findById(1)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            model.setCreatedBy(user);


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/show")
    public ResponseEntity<?> getModel(){
        try{
            return ResponseEntity.ok(modelService.getAllModels());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in deleting model: " + e.getMessage());
        }
    }

    @GetMapping("/getmodel/{id}")
    public ResponseEntity<?> singleModel(@PathVariable("id") Integer id){
        return ResponseEntity.ok(modelService.findByModelId(id));
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
    
}
