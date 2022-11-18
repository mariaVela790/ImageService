package com.imageservice.service;

import com.imageservice.model.Image;
import com.imageservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
