package com.imageservice.repository;

import com.imageservice.entity.DetectedObjectEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectedObjectRepository {
    List<DetectedObjectEntity> findAllByImageId(Long imageId);

    List<Long> getObjectIdsByObjects(List<String> objects);

    int save(DetectedObjectEntity detectedObjectEntity);

    int saveObjects(List<DetectedObjectEntity> detectedObjectEntities);

    Long saveObjectReturnId(DetectedObjectEntity detectedObjectEntity);

    List<Long> saveObjectsReturnIds(List<DetectedObjectEntity> detectedObjectEntities);
}
