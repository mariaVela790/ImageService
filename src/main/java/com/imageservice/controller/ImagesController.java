package com.imageservice.controller;

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

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ImagesController {

//    @Autowired
//    @NonNull
//    private VisionService visionService;

    @Autowired
    @NonNull
    private ImageService imageService;

    @Autowired
    @NonNull
    private JdbcImageRepository imageRepository;
//
//    @Autowired
//    @NonNull
//    private DetectedObjectRepository detectedObjectRepository;

    @GetMapping(
            path = "/images",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GetResponse> getImageSearch(@RequestParam(required = false) List<String> objects) {
        if(objects != null) {
            for(String object : objects) {
                System.out.println(object);
            }
        }

        GetResponse getResponse = GetResponse.builder().build();
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }

    @GetMapping(
            path = "/images/{imageId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GetResponse> getImage(@PathVariable String imageId) {
        if (!imageId.isEmpty()){
            System.out.printf(imageId);
        }
        GetResponse getResponse = GetResponse.builder().build();
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }

    @PostMapping(
            path = "/images",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PostResponse> postImages(@RequestBody PostRequest request){
        // Choosing MULTIPLART_FORM_DATA_VALUE because we want to send in a file and possibly a label and objectDetection

        System.out.println(request.toString());
        System.out.println(request.getLabel());
        System.out.println(request.getEnableDetection());
        System.out.println(request.getImageUrl());

//        if(request.getImage() == null && request.getImageUri() == null) {
        if(request.getImageUrl() == null && request.getFilePath() == null) {
            // TODO change to bad request
            throw new IllegalArgumentException("Image file or external image url is required.");
        }

//        if(request.getImage() != null && request.getImageUri() != null && !request.getImageUri().isEmpty()) {
//        if(request.getImage() != null) {
//            // TODO change to bad request
//            throw new IllegalArgumentException("Only one of image of image uri can be specified");
//        }


        Image image = Image.builder()
                .imageUrl(request.getImageUrl())
                .filePath(request.getFilePath())
                .label(request.getLabel())
                .build();

        PostResponse response = imageService.analyzeImage(image, request.getEnableDetection());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
