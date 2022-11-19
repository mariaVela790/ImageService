package com.imageservice.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "detected_objects")
public class DetectedObjectDao {

    @Id
    @GeneratedValue
    private String objectId;

    @Column(name = "object")
    private String object;

    @ManyToMany(mappedBy = "detectedObjects")
    private Set<ImageDao> images;
}
