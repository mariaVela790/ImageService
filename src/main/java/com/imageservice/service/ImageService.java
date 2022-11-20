package com.imageservice.service;

import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageEntity;
import com.imageservice.model.Image;
import com.imageservice.model.PostResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

//    @Autowired
//    private ImageRepository imageRepository;

//    @Autowired
//    private ImageRepository imageRepository;
//
//    @Autowired
//    private DetectedObjectRepository objectRepository;

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

    private List<String> getDetectedObject(Image image) {
        List<String> detectedObjects = new ArrayList<>();

//        try {
            // TODO Uncomment when ready to test with api
//            List<AnnotateImageResponse> response =  visionService.getImageAnnotations(image.getFilePath(), image.getImageUrl()).get();
            // map response from vision service to list of objects using streaming api

        detectedObjects.add("cat");
        detectedObjects.add("kitten");
        detectedObjects.add("villain");



//        } catch (ExecutionException | InterruptedException e) {
//            System.out.print("Error getting response from vision service ");
//            e.printStackTrace();
//
//        }

        return detectedObjects;
    }

    // logic to get annotations
    public PostResponse analyzeImage(Image image, boolean enableDetection) {

        if (image.getLabel() == null || image.getLabel().isEmpty()) {
            System.out.println("Label not detected! Generating new label");
            String generatedLabel = generateLabel();
            image.setLabel(generatedLabel);
        }

        if (enableDetection) {
            System.out.println("Detection enabled!");
            List<String> detectedObjects = getDetectedObject(image);
            image.setObjectsDetected(detectedObjects);
        }

        // map image to entity and save
        List<String> objects = image.getObjectsDetected();
        List<DetectedObjectEntity> detectedObjectEntities = new ArrayList<>();

        for(String object : objects) {
            DetectedObjectEntity detectedObject = DetectedObjectEntity.builder().object(object).build();
            detectedObjectEntities.add(detectedObject);
//            objectRepository.save(detectedObject);
        }

        ImageEntity imageEntity = ImageEntity.builder()
                .label(image.getLabel())
                .detectedObjects(detectedObjectEntities)
                .build();

//        imageRepository.save(imageEntity);


        return PostResponse.builder()
                .imageId(1234L)
                .label(image.getLabel())
                .detectedObjects(image.getObjectsDetected())
                .build();
    }


    // logic to save to the db

}
