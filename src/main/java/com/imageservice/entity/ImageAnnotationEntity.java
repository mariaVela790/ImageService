package com.imageservice.entity;

import lombok.*;

import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image_annotations")
public class ImageAnnotationEntity {
    private Long objectId;

    private Long imageId;

    private String object;

    private Float score;

    private Float topicality;
}
