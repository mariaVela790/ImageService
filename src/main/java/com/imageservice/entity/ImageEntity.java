package com.imageservice.entity;

import com.imageservice.model.Annotation;
import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class ImageEntity {

    private Long imageId;

    private String label;

    @Nullable
    private List<DetectedObjectEntity> detectedObjects;

    @Nullable
    private List<ImageAnnotationEntity> annotations;
}