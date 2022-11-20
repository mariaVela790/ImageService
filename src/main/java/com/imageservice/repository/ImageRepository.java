package com.imageservice.repository;

import com.imageservice.entity.ImageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepository {

    int count();

    int save(ImageEntity imageEntity);

    int update(ImageEntity imageEntity);

    int deleteById(Long id);

    List<ImageEntity> findAll();

//    List<ImageEntity> findByDe

//    ImageEntity findByImageId(Long imageId);
//
//    ImageEntity findAllByDetectedObjects(List<DetectedObjectEntity> detectedObjectEntityList);

//    List<Image> findBy
}
