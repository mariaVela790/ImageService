package com.imageservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String image;

    private String label;

    @JsonProperty("enableDetection")
    private Boolean enableDetection;

}
