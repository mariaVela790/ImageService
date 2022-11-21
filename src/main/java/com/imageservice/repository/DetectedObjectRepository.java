package com.imageservice.repository;

import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageAnnotationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectedObjectRepository {
    List<DetectedObjectEntity> findAllByImageId(Long imageId);

    String findByObjectId(Long objectId);
    Long findByObject(String object);

    List<Long> findObjectIdsByObjects(List<String> objects);

    Long saveObjectReturnId(ImageAnnotationEntity annotation);

    ImageAnnotationEntity saveObjectReturnAnnotation(ImageAnnotationEntity annotation);

    List<ImageAnnotationEntity> saveObjectsReturnsAnnotations(List<ImageAnnotationEntity> annotationEntities);
}
