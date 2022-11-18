package com.imageservice.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class DetectedObject {

    @Id
    @GeneratedValue
    private Long detectedObjectId;

    private String detectedObjectName;
}
