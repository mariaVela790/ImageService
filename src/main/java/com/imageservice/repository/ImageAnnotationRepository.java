package com.imageservice.repository;

import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageAnnotationEntity;
import com.imageservice.entity.ImageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageAnnotationRepository {

    int save(ImageAnnotationEntity imageAnnotation, Long imageObjectId);

//    Long saveObjectReturnId(ImageAnnotationEntity annotation);

//    DetectedObjectEntity saveAnnotationReturnObject(ImageAnnotationEntity annotation);

//    List<De> saveObjectsReturnIds(List<ImageAnnotationEntity> annotationEntities);
//    List<DetectedObjectEntity> saveAnnotationsReturnObjects(List<ImageAnnotationEntity> annotationEntities);

//    int save(List<ImageAnnotationEntity> imageAnnotations);

//    int update(ImageAnnotationEntity imageEntity);

//    int deleteById(Long imageId, Long objectId);

    List<ImageAnnotationEntity> findByImageId(Long imageId);

}
