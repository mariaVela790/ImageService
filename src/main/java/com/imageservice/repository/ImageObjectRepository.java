package com.imageservice.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageObjectRepository {

    List<Long> getObjectIdsByImageId(Long imageId);

    List<Long> getImageIdsByObjectIds(List<Long> objectIds);

    Long saveReturnImageObjectId(Long objectId, Long imageId);

}
