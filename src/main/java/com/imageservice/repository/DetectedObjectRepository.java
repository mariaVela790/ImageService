package com.imageservice.repository;

import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageAnnotationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectedObjectRepository {
    List<DetectedObjectEntity> findAllByImageId(Long imageId);

    List<Long> getObjectIdsByObjects(List<String> objects);

    Long saveObjectReturnId(ImageAnnotationEntity annotation);

    ImageAnnotationEntity saveObjectReturnAnnotation(ImageAnnotationEntity annotation);

    List<ImageAnnotationEntity> saveObjectsReturnsAnnotations(List<ImageAnnotationEntity> annotationEntities);
}
