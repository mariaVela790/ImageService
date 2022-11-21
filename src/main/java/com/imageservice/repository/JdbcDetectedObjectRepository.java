package com.imageservice.repository;

import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageAnnotationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcDetectedObjectRepository implements DetectedObjectRepository{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcImageObjectRepository imageObjectRepository;

    @Override
    public List<DetectedObjectEntity> findAllByImageId(Long imageId) {
        List<Long> objectIds = imageObjectRepository.getObjectIdsByImageId(imageId);

        return namedParameterJdbcTemplate.query("SELECT * FROM objects WHERE object_id IN (:objectIds)",
                new MapSqlParameterSource("objectIds", objectIds),
                (result, rowNumber) ->
                        new DetectedObjectEntity(result.getLong("object_id"), result.getString("object"))
        );
    }

    @Override
    public List<Long> getObjectIdsByObjects(List<String> objects) {
        return namedParameterJdbcTemplate.query("SELECT (object_id) FROM objects WHERE object IN (:objects)",
                new MapSqlParameterSource("objects", objects),
                (rs, rowNum) -> rs.getLong("object_id"));
    }

    @Override
    public Long saveObjectReturnId(ImageAnnotationEntity annotation) {
        KeyHolder key = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("INSERT INTO objects (object) VALUES (:object)",
                new MapSqlParameterSource("object", annotation.getObject()),
                key);

        return Objects.requireNonNull(key.getKey()).longValue();
    }

    @Override
    public ImageAnnotationEntity saveObjectReturnAnnotation(ImageAnnotationEntity annotation) {
        KeyHolder key = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("INSERT INTO objects (object) VALUES (:object)",
                new MapSqlParameterSource("object", annotation.getObject()),
                key);

        annotation.setObjectId(Objects.requireNonNull(key.getKey()).longValue());

        return annotation;
    }

    @Override
    public List<ImageAnnotationEntity> saveObjectsReturnsAnnotations(List<ImageAnnotationEntity> annotationEntities) {
        List<ImageAnnotationEntity> annotations = new ArrayList<>();
        for (ImageAnnotationEntity annotationEntity : annotationEntities) {
            ImageAnnotationEntity annotation = saveObjectReturnAnnotation(annotationEntity);
            annotations.add(annotation);
        }
        return annotations;
    }
}
