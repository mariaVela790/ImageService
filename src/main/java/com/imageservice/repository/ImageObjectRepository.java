package com.imageservice.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageObjectRepository {

    List<Long> getObjectIdsByImageId(Long imageId);

    List<Long> getImageIdsByObjectIds(List<Long> objectIds);

    int save(Long objectId, Long imageId);

    int save(List<Long> objectIds, Long imageId);
}
