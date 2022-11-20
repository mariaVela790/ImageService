package com.imageservice.repository;

import com.imageservice.entity.ImageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepository {

    int save(ImageEntity imageEntity);

    int update(ImageEntity imageEntity);

    int deleteById(Long id);

    List<ImageEntity> findAll();

    ImageEntity findByImageId(Long imageId);

    List<ImageEntity> findImagesByObjects(List<String> objects);

    ImageEntity saveImageWithObjects(ImageEntity image);

}
