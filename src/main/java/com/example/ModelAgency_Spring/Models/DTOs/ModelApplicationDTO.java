package com.example.ModelAgency_Spring.Models.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;


public class ModelApplicationDTO {
    private String name;
    private String email;
    private String phone;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String gender;
    private double weight;
    private double height;
    private String hair;
    private double hips;
    private String eyeColor;
    private String description;
//    private MultipartFile imgUrl1;
//    private MultipartFile imgUrl2;
//    private MultipartFile imgUrl3;
    private double rate;
    private String category;

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getLocation() {
        return location;
    }
    public Date getDob() {
        return dob;
    }
    public String getGender() {
        return gender;
    }
    public double getWeight() {
        return weight;
    }
    public double getHeight() {
        return height;
    }
    public String getHair() {
        return hair;
    }
    public double getHips() {
        return hips;
    }
    public String getEyeColor() {
        return eyeColor;
    }
    public String getDescription() {
        return description;
    }
//    public MultipartFile getImgUrl1() {
//        return imgUrl1;
//    }
//    public MultipartFile getImgUrl2() {
//        return imgUrl2;
//    }
//    public MultipartFile getImgUrl3() {
//        return imgUrl3;
//    }
    public double getRate() {
        return rate;
    }
    public String getCategory() {
        return category;
    }

}
