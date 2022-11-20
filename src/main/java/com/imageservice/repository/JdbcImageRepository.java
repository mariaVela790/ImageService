package com.imageservice.repository;

import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcImageRepository implements ImageRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcDetectedObjectRepository objectRepository;

    @Autowired
    private JdbcImageObjectRepository imageObjectRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM IMAGES", Integer.class);
    }

    @Override
    public int save(ImageEntity imageEntity) {

        return 0;
    }

    @Override
    public int update(ImageEntity imageEntity) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public List<ImageEntity> findAll() {

        return jdbcTemplate.query("SELECT * FROM images",
                (rs, rowNum) ->
                    ImageEntity.builder()
                            .imageId(rs.getLong("image_id"))
                            .label(rs.getString("label"))
                            .detectedObjects(objectRepository.findAllByImageId(rs.getLong("image_id")))
                            .build()
                );
    }

    @Override
    public ImageEntity findByImageId(Long imageId) {

        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM images WHERE image_id = :id",
                new MapSqlParameterSource("id", imageId),
                (rs, rowNum) ->
                        ImageEntity.builder()
                                .imageId(rs.getLong("image_id"))
                                .label(rs.getString("label"))
                                .detectedObjects(objectRepository.findAllByImageId(imageId))
                                .build()
                );
    }

    @Override
    public List<ImageEntity> findImagesByObjects(List<String> objects) {
        List<Long> objectIds = objectRepository.getObjectIdsByObjects(objects);
        List<Long> imageIds = imageObjectRepository.getImageIdsByObjectIds(objectIds);
        return namedParameterJdbcTemplate.query("SELECT * FROM images WHERE image_id IN (:imageIds)",
                new MapSqlParameterSource("imageIds", imageIds),
                (rs, rowNum) ->
                    ImageEntity.builder()
                            .imageId(rs.getLong("image_id"))
                            .label(rs.getString("label"))
                            .detectedObjects(objectRepository.findAllByImageId(rs.getLong("image_id")))
                            .build());
    }

    @Override
    public Long saveImageReturnId(ImageEntity image) {
        return null;
    }

}
