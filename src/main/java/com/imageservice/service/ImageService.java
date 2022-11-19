package com.imageservice.service;

import com.imageservice.model.Image;
import com.imageservice.model.PostResponse;
import com.imageservice.repository.ImageRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private VisionService visionService;

    // example on using the repository
//    public List<Image> getAllImages() {
//        return imageRepository.findAll();
//    }

    // logic to generate label
    private String generateLabel() {
        String filename = "";
        String randomChars = RandomStringUtils.randomAlphanumeric(16);
        String datetime = new Date().toString();
        filename = randomChars + "_" + datetime;

        return filename;
    }

    // logic to get annotations
    public PostResponse analyzeImage()


    // logic to save to the db

}
