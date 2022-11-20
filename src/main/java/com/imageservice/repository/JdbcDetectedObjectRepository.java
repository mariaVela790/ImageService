package com.imageservice.repository;

import com.imageservice.entity.DetectedObjectEntity;
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

//    @Override
//    public List<Long> getObjectIdsByDetectedObjects(List<DetectedObjectEntity> objectEntities) {
//        return namedParameterJdbcTemplate.query("SELECT (object_id) FROM objects WHERE object IN (:objects)",
//                new MapSqlParameterSource("objects", objectEntity.getObject()),
//                (rs, rowNum) -> rs.);
//    }

    @Override
    public int save(DetectedObjectEntity detectedObjectEntity) {
        return 0;
    }

    @Override
    public int saveObjects(List<DetectedObjectEntity> detectedObjectEntities) {
        return 0;
    }

    @Override
    public Long saveObjectReturnId(DetectedObjectEntity detectedObjectEntity) {
        KeyHolder key = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("INSERT INTO objects (object) VALUES (:object)",
                new MapSqlParameterSource("object", detectedObjectEntity.getObject()),
                key);

        return Objects.requireNonNull(key.getKey()).longValue();
    }

    @Override
    public List<Long> saveObjectsReturnIds(List<DetectedObjectEntity> detectedObjectEntities) {
        List<Long> savedObjectIds = new ArrayList<>();
        for (DetectedObjectEntity detectedObjectEntity : detectedObjectEntities) {
            savedObjectIds.add(saveObjectReturnId(detectedObjectEntity));
        }
        return savedObjectIds;
    }
}
