package com.imageservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@AllArgsConstructor
public class Request {
    private String imageUrl;

    private String filePath;

    private String label;

    @NonNull
    private Boolean enableDetection;

}
