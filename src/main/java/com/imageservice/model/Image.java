package com.imageservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Image {
    private Long imageId;

    @JsonIgnore
    private String filePath;

    @JsonIgnore
    private String imageUrl;

    private String label;

    // TODO make sure the annotations are null if object detection was not enabled
    private List<Annotation> annotations;
}
