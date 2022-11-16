package com.imageservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
public class PostRequest {
    // object for POST /images the has the following
    // image file
    // OR image URL
    // optional label for the image
    // option field to enable object detection
    @NotNull
    private MultipartFile file;

    private String label;

    private boolean enableDetection;

}
