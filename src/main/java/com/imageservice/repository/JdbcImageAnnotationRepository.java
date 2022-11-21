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

//    @Override
//    public Long saveObjectReturnId(ImageAnnotationEntity annotation) {
//        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
//        mapSqlParameterSource.addValue("image_id", annotation.getImageId());
//        mapSqlParameterSource.addValue("object_id", annotation.getObjectId());
//        mapSqlParameterSource.addValue("score", annotation.getScore());
//        mapSqlParameterSource.addValue("topicality", annotation.getTopicality());
//
//        KeyHolder key = new GeneratedKeyHolder();
//
//        namedParameterJdbcTemplate.update("INSERT INTO image_annotations (image_id, object_id, score, topicality) VALUES (:image_id, :object_id, :score, :topicality)",
//                mapSqlParameterSource,
//                key);
//
//        return Objects.requireNonNull(key.getKey()).longValue();
//    }

//    @Override
//    public DetectedObjectEntity saveAnnotationReturnObject(ImageAnnotationEntity annotation) {
////        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
////        mapSqlParameterSource.addValue("image_id", annotation.getImageId());
////        mapSqlParameterSource.addValue("object_id", annotation.getObjectId());
////        mapSqlParameterSource.addValue("score", annotation.getScore());
////        mapSqlParameterSource.addValue("topicality", annotation.getTopicality());
////
////        KeyHolder key = new GeneratedKeyHolder();
////
////        namedParameterJdbcTemplate.update("INSERT INTO image_annotations (image_id, object_id, score, topicality) VALUES (:image_id, :object_id, :score, :topicality)",
////                mapSqlParameterSource,
////                key);
//
//        return DetectedObjectEntity.builder().build();
//    }

//    @Override
//    public List<DetectedObjectEntity> saveAnnotationsReturnObjects(List<ImageAnnotationEntity> annotationEntities) {
//        List<DetectedObjectEntity> detectedObjectEntities = new ArrayList<>();
//
////        for (ImageAnnotationEntity annotationEntity : annotationEntities) {
////            saveObjectReturnId()
////        }
//
//        return null;
//    }

//    @Override
//    public int save(List<ImageAnnotationEntity> imageAnnotations) {
//        for (ImageAnnotationEntity imageAnnotation : imageAnnotations) {
////            save(imageAnnotation);
//        }
//        return 0;
//    }
//
//    @Override
//    public int update(ImageAnnotationEntity imageEntity) {
//        return 0;
//    }
//
//    @Override
//    public int deleteById(Long imageId, Long objectId) {
//        return 0;
//    }

    @Override
    public List<ImageAnnotationEntity> findByImageId(Long imageId) {
        return namedParameterJdbcTemplate.query("SELECT * FROM image_annotations WHERE image_id = :imageid",
                new MapSqlParameterSource("imageid", imageId),
                (rs, rowNum) -> ImageAnnotationEntity.builder()
                        .objectId(rs.getLong("object_id"))
                        .imageId(rs.getLong("image_id"))
                        .score(rs.getFloat("score"))
                        .topicality(rs.getFloat("topicality"))
                        .build());
    }
}
