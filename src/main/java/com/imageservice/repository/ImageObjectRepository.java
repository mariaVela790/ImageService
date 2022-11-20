package com.imageservice.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageObjectRepository {

    List<Long> getObjectIdsByImageId(Long imageId);

    List<Long> getImageIdsbyObjectId(Long objectId);

    List<Long> getImageIdsByObjectIds(List<Long> objectIds);

    int save(Long object_id, Long imageId);
}
