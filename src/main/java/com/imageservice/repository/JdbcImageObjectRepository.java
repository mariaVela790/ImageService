package com.imageservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcImageObjectRepository implements ImageObjectRepository{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Long> getObjectIdsByImageId(Long imageId) {
        return namedParameterJdbcTemplate.query("SELECT (object_id) FROM image_objects WHERE image_id = :id",
                new MapSqlParameterSource("id", imageId),
                (rs, rowNum) -> rs.getLong("object_id"));
    }

    @Override
    public List<Long> getImageIdsbyObjectId(Long objectId) {
        return namedParameterJdbcTemplate.query("SELECT (image_id) FROM image_objects WHERE object_id = :id",
                new MapSqlParameterSource("id", objectId),
                (rs, rowNum) -> rs.getLong("image_id"));
    }

    @Override
    public List<Long> getImageIdsByObjectIds(Long objectIds) {
        return namedParameterJdbcTemplate.query("SELECT (image_id) FROM image_objects WHERE object_id IN (:objectIds)",
                new MapSqlParameterSource("objectIds", objectIds),
                (rs, rowNum) -> rs.getLong("image_id")
                );
    }

    @Override
    public int save(Long objectId, Long imageId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("object_id", objectId);
        mapSqlParameterSource.addValue("image_id", imageId);
        return namedParameterJdbcTemplate.update("INSERT INTO image_objects (object_id,image_id) VALUES (:object_id, :image_id)",
                mapSqlParameterSource
                );
    }
}
