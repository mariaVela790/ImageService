package com.imageservice.repository;

import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageAnnotationEntity;
import com.imageservice.entity.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcImageAnnotationRepository implements ImageAnnotationRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcDetectedObjectRepository objectRepository;

    @Autowired
    private JdbcImageObjectRepository imageObjectRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public int save(ImageAnnotationEntity imageAnnotation, Long imageObjectId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("image_object_id", imageObjectId);
        mapSqlParameterSource.addValue("score", imageAnnotation.getScore());
        mapSqlParameterSource.addValue("topicality", imageAnnotation.getTopicality());

        return namedParameterJdbcTemplate.update("INSERT INTO image_annotations (image_object_id, score, topicality) VALUES (:image_object_id, :score, :topicality)",
                mapSqlParameterSource);
    }

    @Override
    public ImageAnnotationEntity findByImageObjectId(Long imageObjectId) {
        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM image_annotations WHERE image_object_id = :imageobjectid",
                new MapSqlParameterSource("imageobjectid", imageObjectId),
                (rs, rowNum) -> ImageAnnotationEntity.builder()
                        .imageId(rs.getLong("image_object_id"))
                        .object(
                                objectRepository.findByObjectId(imageObjectRepository.findObjectIdByImageObjectId(rs.getLong("image_object_id")))
                        )
                        .score(rs.getFloat("score"))
                        .topicality(rs.getFloat("topicality"))
                        .build());
    }

    @Override
    public List<ImageAnnotationEntity> findByImageId(Long imageId) {
        List<Long> imageObjectIds = imageObjectRepository.findImageObjectIdByImageId(imageId);
        List<ImageAnnotationEntity> annotationEntities = new ArrayList<>();

        for (Long imageObjectId : imageObjectIds) {
            annotationEntities.add(findByImageObjectId(imageObjectId));
        }

        return annotationEntities;
    }
}
