package com.imageservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Annotation {
    @JsonIgnore
    private Long objectId;

    private String object;

    private Float score;

    private Float topicality;
}
