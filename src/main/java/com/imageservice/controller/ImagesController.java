package com.imageservice.controller;

import com.imageservice.model.GetResponse;
import com.imageservice.model.ImageFile;
import com.imageservice.model.PostRequest;
import com.imageservice.model.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.Soundbank;
import javax.validation.Valid;
import java.util.List;


@RestController
public class ImagesController {

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
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PostResponse> postImages(@ModelAttribute PostRequest request){
        // Choosing MULTIPLART_FORM_DATA_VALUE because we want to send in a file and possibly a label and objectDetection

        System.out.println(request.getLabel());
        System.out.println(request.isEnableDetection());
        System.out.println(request.getFile());


        ImageFile image = ImageFile.builder()
                .filePath("/file/path")
                .build();

        PostResponse response = PostResponse.builder()
                .imageId("123")
                .image(image)
                .label("genLabel")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}