package com.imageservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Image {
    private Long imageId;

    private String filePath;

    private String imageUrl;

    private String label;

    // TODO make sure the annotations are null if object detection was not enabled
    private List<String> objectsDetected;
}
