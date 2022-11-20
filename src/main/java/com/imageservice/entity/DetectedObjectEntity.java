package com.imageservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

//@Entity


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "objects")
public class DetectedObjectEntity {

//    @Id
//    @GeneratedValue
    private Long objectId;

//    @Column(name = "object")
    private String object;

//    @ManyToMany(mappedBy = "detectedObjects")
//    private List<ImageEntity> images;
}
