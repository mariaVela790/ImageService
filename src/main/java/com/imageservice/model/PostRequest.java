package com.imageservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class PostRequest {
    // object for POST /images the has the following
    // image file
    // OR image URL
    // optional label for the image
    // option field to enable object detection
    // TODO boil down to use Image class
    private String imageUrl;

    private String filePath;

    private String label;

    @JsonProperty("enableDetection")
    private Boolean enableDetection;

}
