package com.imageservice.entity;

import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;


//@Entity

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class ImageEntity {
//    @Id
//    @GeneratedValue
    private Long imageId;

//    @Column(name = "image_file")
//    private String image;

//    @Column(name = "label")
    private String label;

//    @ManyToMany(cascade = CascadeType.MERGE)
//    @JoinTable(
//            name = "image_objects",
//            joinColumns = @JoinColumn(name = "image_id"),
//            inverseJoinColumns = @JoinColumn(name = "object_id")
//    )
    @Nullable
    private List<DetectedObjectEntity> detectedObjects;
}