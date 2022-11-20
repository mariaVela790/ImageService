package com.imageservice.repository;

import com.imageservice.entity.DetectedObjectEntity;
import com.imageservice.entity.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcImageRepository implements ImageRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

        return jdbcTemplate.query("SELECT * FROM IMAGES",
                (rs, rowNum) ->
                    new ImageEntity(
                            rs.getLong("image_id"),
                            rs.getString("label"),
                            jdbcTemplate.query("SELECT * FROM objects WHERE object_id IN (SELECT object_id FROM image_objects LEFT JOIN images ON image_objects.image_id = images.image_id )",
                                    (result, rowNumber) ->
                                        new DetectedObjectEntity(result.getLong("object_id"), result.getString("object"))
                                    )
                    )
                );
    }
}
