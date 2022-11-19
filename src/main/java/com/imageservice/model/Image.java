package com.imageservice.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Builder
@Data
public class Image {
    // image file OR url
    // image labels
    @Id
    @GeneratedValue
    private String imageId;

    private String filePath;

    private String label;

    // TODO make sure the annotations are null if object detection was not enabled
    private List<DetectedObject> objectsDetected;
}
