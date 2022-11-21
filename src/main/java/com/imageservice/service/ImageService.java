package com.imageservice.service;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageAnnotationEntity;
import com.imageservice.entity.ImageEntity;
import com.imageservice.model.*;
import com.imageservice.repository.DetectedObjectRepository;
import com.imageservice.repository.ImageAnnotationRepository;
import com.imageservice.repository.ImageObjectRepository;
import com.imageservice.repository.JdbcImageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    private ImageObjectRepository imageObjectRepository;

    @Autowired
    private ImageAnnotationRepository imageAnnotationRepository;

    private String generateLabel() {
        String filename = "";
        String randomChars = RandomStringUtils.randomAlphanumeric(5);
        String datetime = Arrays.toString(new Date().toString().split(" "));
        filename = randomChars + "_" + datetime;

        return filename;
    }

    private List<Annotation> getAnalysis(Image image) {
        List<Annotation> annotations = new ArrayList<>();

        try {
            // TODO Uncomment when ready to test with api
            List<AnnotateImageResponse> response =  visionService.getImageAnnotations(image.getFilePath(), image.getImageUrl()).get();

            List<EntityAnnotation> entityAnnotations = response.get(0).getLabelAnnotationsList();


            for (EntityAnnotation entityAnnotation : entityAnnotations) {
                Annotation annotation = Annotation.builder()
                        .object(entityAnnotation.getDescription())
                        .score(entityAnnotation.getScore())
                        .topicality(entityAnnotation.getTopicality())
                        .build();
                annotations.add(annotation);
            }

        } catch (ExecutionException | InterruptedException e) {
            System.out.print("Error getting response from vision service ");
            e.printStackTrace();

        }

        return annotations;
    }

    private ImageEntity analyzeImage(Image image, boolean enableDetection) {

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
            List<Annotation> annotations = getAnalysis(image);
            List<ImageAnnotationEntity> imageAnnotations = new ArrayList<>();
            List<DetectedObjectEntity> detectedObjects = new ArrayList<>();
            for(Annotation annotation : annotations) {
                ImageAnnotationEntity annotationEntity = ImageAnnotationEntity.builder()
                        .object(annotation.getObject())
                        .topicality(annotation.getTopicality())
                        .score(annotation.getScore())
                        .build();

                imageAnnotations.add(annotationEntity);
                // need to save annotations
                DetectedObjectEntity detectedObjectEntity = DetectedObjectEntity.builder()
                        .object(annotation.getObject())
                        .build();

                detectedObjects.add(detectedObjectEntity);

            }
            analyzedImage.setDetectedObjects(detectedObjects);
            analyzedImage.setAnnotations(imageAnnotations);
        }

        return analyzedImage;
    }

    public List<Image> persistImage(Request request) {

        Image image = Image.builder()
                .imageUrl(request.getImageUrl())
                .filePath(request.getFilePath())
                .label(request.getLabel())
                .build();

        ImageEntity imageSaved = ImageEntity.builder().build();

        if(request.getEnableDetection()) {
            ImageEntity analysisResponse = analyzeImage(image, request.getEnableDetection());

            imageSaved = imageRepository.saveImageWithObjects(analysisResponse);
        } else {
            imageSaved = imageRepository.save(image);
        }


        List<Annotation> annotations = new ArrayList<>();
        List<Image> response = new ArrayList<>();
        Image imageResponse = null;

        if (imageSaved != null) {

            System.out.println("image not null");

            if (imageSaved.getAnnotations() != null) {
                for (ImageAnnotationEntity annotation : imageSaved.getAnnotations()) {
                    Annotation imgAnnotation = Annotation.builder()
                            .object(annotation.getObject())
                                    .topicality(annotation.getTopicality())
                                            .score(annotation.getScore()).build();

                    annotations.add(imgAnnotation);
                }
            }

            imageResponse = Image.builder()
                    .imageId(imageSaved.getImageId())
                    .label(imageSaved.getLabel())
                    .annotations(annotations)
                    .build();

            response.add(imageResponse);

        }
        return response;
    }

    public List<Image> getImage(Long imageId) {
        List<Image> images = new ArrayList<>();
        if (imageId != null){

            ImageEntity imageEntity = imageRepository.findByImageId(imageId);
            List<Long> imageObjectIds = imageObjectRepository.findImageObjectIdByImageId(imageId);
            List<ImageAnnotationEntity> annotationEntities = new ArrayList<>();
            List<Annotation> annotations = new ArrayList<>();


            for(Long imageObjectId : imageObjectIds) {
                annotationEntities.add(imageAnnotationRepository.findByImageObjectId(imageObjectId));
            }

            for (ImageAnnotationEntity annotationEntity : annotationEntities) {
                Annotation annotation = Annotation.builder()
                        .object(annotationEntity.getObject())
                        .score(annotationEntity.getScore())
                        .topicality(annotationEntity.getTopicality())
                        .build();

                annotations.add(annotation);
            }


            if (imageEntity != null) {
                Image img = Image.builder()
                        .imageId(imageEntity.getImageId())
                        .label(imageEntity.getLabel())
                        .annotations(annotations)
                        .build();
                images.add(img);
            }

        }

        return images;
    }

    public List<Image> getImages(List<String> objects){

        List<ImageEntity> imageEntities = imageRepository.findImagesByObjects(objects);

        List<Image> imagesWithObjects = new ArrayList<>();

        for (ImageEntity imageEntity : imageEntities) {
            List<Annotation> annotations = new ArrayList<>();
            for (ImageAnnotationEntity annotation : imageEntity.getAnnotations()) {
                Annotation imageAnnotation = Annotation.builder()
                        .object(annotation.getObject())
                        .topicality(annotation.getTopicality())
                        .score(annotation.getScore())
                        .build();

                annotations.add(imageAnnotation);

            }


            Image img = Image.builder()
                    .imageId(imageEntity.getImageId())
                    .label(imageEntity.getLabel())
                    .annotations(annotations)
                    .build();

            imagesWithObjects.add(img);
        }
        return imagesWithObjects;
    }

    public List<Image> getImages(){

        List<ImageEntity> imageEntities = imageRepository.findAll();

        List<Image> imagesWithObjects = new ArrayList<>();

        for (ImageEntity imageEntity : imageEntities) {
            List<Annotation> annotations = new ArrayList<>();
            for (ImageAnnotationEntity annotation : imageEntity.getAnnotations()) {
                Annotation imageAnnotation = Annotation.builder()
                        .object(annotation.getObject())
                        .topicality(annotation.getTopicality())
                        .score(annotation.getScore())
                        .build();

                annotations.add(imageAnnotation);

            }


            Image img = Image.builder()
                    .imageId(imageEntity.getImageId())
                    .label(imageEntity.getLabel())
                    .annotations(annotations)
                    .build();

            imagesWithObjects.add(img);
        }
        return imagesWithObjects;
    }
}
