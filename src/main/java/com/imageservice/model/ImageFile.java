package com.imageservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class ImageFile {
    // image file OR url
    // image labels
    @NotNull(message = "File is required")
    private String filePath;

    // TODO make sure the annotations are null if object detection was not enabled
    private List<String> annotations;
}
