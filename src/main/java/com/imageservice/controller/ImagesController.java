package com.imageservice.controller;

import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageEntity;
import com.imageservice.model.Response;
import com.imageservice.model.Image;
import com.imageservice.model.Request;
import com.imageservice.repository.JdbcImageRepository;
import com.imageservice.service.ImageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    public ResponseEntity<Response> getImageSearch(@Validated @RequestParam(required = false) String objects) {
        if(objects != null) {
            String cleanString = objects.replaceAll("\"", "");
            String[] strSplit = cleanString.split(",");
            sout

            List<String> objectList = new ArrayList<String>(Arrays.asList(strSplit));
            List<Image> imagesWithObjects = imageService.getImages(objectList);

            Response response = Response.builder().images(imagesWithObjects).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<Image> images = imageService.getImages();

        Response getResponse = Response.builder().images(images).build();
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }

    @GetMapping(
            path = "/images/{imageId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Response> getImage(@Validated @PathVariable Long imageId) {

        Response getResponse = Response.builder()
                .images(imageService.getImage(imageId))
                .build();

        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }

    @PostMapping(
            path = "/images",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Response> postImages(@Validated @RequestBody Request request){

        if(request.getImageUrl() == null && request.getFilePath() == null) {
            // TODO change to bad request
            throw new IllegalArgumentException("Image file or external image url is required.");
        }

        Response response = Response.builder()
                .images(imageService.persistImage(request))
                .build();


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
