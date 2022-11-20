package com.imageservice.service;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageEntity;
import com.imageservice.model.Image;
import com.imageservice.model.PostResponse;
import com.imageservice.repository.JdbcImageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Autowired
    JdbcImageRepository imageRepository;

    @Autowired
    private VisionService visionService;

    private String generateLabel() {
        String filename = "";
        String randomChars = RandomStringUtils.randomAlphanumeric(16);
        String datetime = new Date().toString();
        filename = randomChars + "_" + datetime;

        return filename;
    }

    private List<DetectedObjectEntity> getDetectedObjects(Image image) {
        List<DetectedObjectEntity> detectedObjects = new ArrayList<>();

        try {
            // TODO Uncomment when ready to test with api
            List<AnnotateImageResponse> response =  visionService.getImageAnnotations(image.getFilePath(), image.getImageUrl()).get();

            List<EntityAnnotation> entityAnnotations = response.get(0).getLabelAnnotationsList();


            for (EntityAnnotation entityAnnotation : entityAnnotations) {
                DetectedObjectEntity detectedObject = DetectedObjectEntity.builder()
                        .object(entityAnnotation.getDescription())
                        .build();
                detectedObjects.add(detectedObject);
            }

        } catch (ExecutionException | InterruptedException e) {
            System.out.print("Error getting response from vision service ");
            e.printStackTrace();

        }

        return detectedObjects;
    }

    public ImageEntity analyzeImage(Image image, boolean enableDetection) {

        ImageEntity analyzedImage = ImageEntity.builder().build();

        if (image.getLabel() == null || image.getLabel().isEmpty()) {
            System.out.println("Label not detected! Generating new label");
            String generatedLabel = generateLabel();
            analyzedImage.setLabel(generatedLabel);
        } else {
            analyzedImage.setLabel(image.getLabel());
        }

        if (enableDetection) {
            System.out.println("Detection enabled!");
            List<DetectedObjectEntity> detectedObjects = getDetectedObjects(image);
            analyzedImage.setDetectedObjects(detectedObjects);
        }

        return analyzedImage;
    }

//    public ImageEntity persistImage() {
//
//    }

}
