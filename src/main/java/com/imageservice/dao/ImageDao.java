package com.imageservice.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "images")
public class ImageDao {
    @Id
    @GeneratedValue
    private String imageId;

    @Column(name = "image_file")
    private String image;

    @Column(name = "label")
    private String label;

    @ManyToMany
    @JoinTable(
            name = "detected_object",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "object_id")
    )
    private Set<DetectedObjectDao> detectedObjects;
}