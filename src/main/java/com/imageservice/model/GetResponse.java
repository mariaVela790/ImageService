package com.imageservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class GetResponse {
    // list of Images
    private List<ImageFile> images;
}
