package com.imageservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class JdbcImageObjectRepository implements ImageObjectRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Long> getObjectIdsByImageId(Long imageId) {
        return namedParameterJdbcTemplate.query("SELECT (object_id) FROM image_objects WHERE image_id = :id",
                new MapSqlParameterSource("id", imageId),
                (rs, rowNum) -> rs.getLong("object_id"));
    }

    @Override
    public List<Long> getImageIdsByObjectIds(List<Long>  objectIds) {
        return namedParameterJdbcTemplate.query("SELECT (image_id) FROM image_objects WHERE object_id IN (:objectids)",
                new MapSqlParameterSource("objectids", objectIds),
                (rs, rowNum) -> rs.getLong("image_id")
                );
    }

    @Override
    public Long saveReturnImageObjectId(Long objectId, Long imageId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("object_id", objectId);
        mapSqlParameterSource.addValue("image_id", imageId);

        KeyHolder key = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update("INSERT INTO image_objects (object_id,image_id) VALUES (:object_id, :image_id)",
                mapSqlParameterSource,
                key);

        return Objects.requireNonNull(key.getKey()).longValue();
    }

    //TODO get rid of this method - no longer needed
//    @Override
//    public int save(List<Long> objectIds, Long imageId) {
//        for (Long objectId : objectIds) {
//            save(objectId, imageId);
//        }
//        return 0;
//    }
}
