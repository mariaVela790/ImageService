package com.imageservice.repository;

import com.imageservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByImageId(String imageId);

//    List<Image> findBy
}
