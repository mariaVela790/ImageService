package com.imageservice.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "objects")
public class DetectedObjectEntity {

    private Long objectId;

    private String object;

}
