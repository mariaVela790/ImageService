package com.imageservice.repository;

import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageAnnotationEntity;
import com.imageservice.entity.ImageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageAnnotationRepository {

    int save(ImageAnnotationEntity imageAnnotation, Long imageObjectId);

    List<ImageAnnotationEntity> findByImageId(Long imageId);

}
