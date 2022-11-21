package com.imageservice.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageObjectRepository {

    List<Long> findObjectIdsByImageId(Long imageId);

    List<Long> findImageIdsByObjectIds(List<Long> objectIds);
    Long findObjectIdByImageObjectId(Long imageObjectIdL);

    List<Long> findImageObjectIdByImageId(Long imageId);

    List<Long> findImageIdsByObjectId(Long objectId);

    Long saveReturnImageObjectId(Long objectId, Long imageId);

}
