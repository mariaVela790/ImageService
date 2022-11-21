package com.imageservice.controller;

import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageEntity;
import com.imageservice.model.GetResponse;
import com.imageservice.model.Image;
import com.imageservice.model.PostRequest;
import com.imageservice.model.PostResponse;
import com.imageservice.repository.JdbcImageRepository;
import com.imageservice.service.ImageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class ImagesController {

    @Autowired
    @NonNull
    private ImageService imageService;

    @Autowired
    @NonNull
    private JdbcImageRepository imageRepository;

    @GetMapping(
            path = "/images",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GetResponse> getImageSearch(@RequestParam(required = false) List<String> objects) {
        if(objects != null) {
            for (String object : objects) {
                System.out.println(object);
            }

            List<ImageEntity> imageEntities = imageRepository.findImagesByObjects(objects);
            List<Image> imagesWithObjects = new ArrayList<>();

//            for (ImageEntity imageEntity : imageEntities) {
//                List<String> imageObjects = new ArrayList<>();
//                for (DetectedObjectEntity detectedObject : imageEntity.getDetectedObjects()) {
//                    imageObjects.add(detectedObject.getObject());
//                }
//
//                System.out.printf("imageObject first element %s", imageObjects.toString());
//
//                Image img = Image.builder()
//                        .imageId(imageEntity.getImageId())
//                        .label(imageEntity.getLabel())
//                        .annotations(imageObjects)
//                        .build();
//
//                imagesWithObjects.add(img);
//            }

            GetResponse response = GetResponse.builder().images(imagesWithObjects).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<ImageEntity> imageEntities = imageRepository.findAll();
        List<Image> images = new ArrayList<>();

        for (ImageEntity imageEntity : imageEntities) {
            List<String> imageObjects = new ArrayList<>();
            for (DetectedObjectEntity detectedObject : imageEntity.getDetectedObjects()) {
                imageObjects.add(detectedObject.getObject());
            }

            System.out.printf("imageObject first element %s", imageObjects.toString());

            Image img = Image.builder()
                    .imageId(imageEntity.getImageId())
                    .label(imageEntity.getLabel())
                    .build();

            images.add(img);
        }


        GetResponse getResponse = GetResponse.builder().images(images).build();
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }

    @GetMapping(
            path = "/images/{imageId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GetResponse> getImage(@PathVariable Long imageId) {
        List<Image> images = new ArrayList<>();
        if (imageId != null){
            ImageEntity imageEntity = imageRepository.findByImageId(imageId);
            List<String> imageObjects = new ArrayList<>();

            if (imageEntity != null) {

                if(imageEntity.getDetectedObjects() != null && !imageEntity.getDetectedObjects().isEmpty()) {
                    for (DetectedObjectEntity detectedObject : imageEntity.getDetectedObjects()) {
                        imageObjects.add(detectedObject.getObject());
                    }
                }
                Image img = Image.builder()
                        .imageId(imageEntity.getImageId())
                        .label(imageEntity.getLabel())
                        .build();
                images.add(img);
            }


        }
        GetResponse getResponse = GetResponse.builder().images(images).build();
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }

    @PostMapping(
            path = "/images",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PostResponse> postImages(@RequestBody PostRequest request){

        if(request.getImageUrl() == null && request.getFilePath() == null) {
            // TODO change to bad request
            throw new IllegalArgumentException("Image file or external image url is required.");
        }

        PostResponse response = imageService.persistImage(request);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
