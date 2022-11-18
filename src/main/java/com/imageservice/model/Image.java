package com.imageservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
public class Image {
    // image file OR url
    // image labels
    @Id
    @GeneratedValue
    private Long imageId;

    private String filePath;

    private String label;

    // TODO make sure the annotations are null if object detection was not enabled
//    private List<DetectedObject> objectsDetected;
}
