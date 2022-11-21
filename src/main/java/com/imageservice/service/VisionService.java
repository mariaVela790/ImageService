package com.imageservice.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisionService {

    private Image getImage(String imagePath, String imageUri) {
        try {
            if (imagePath != null) {
                ByteString imgBytes = ByteString.readFrom(Files.newInputStream(Paths.get(imagePath)));
                return Image.newBuilder().setContent(imgBytes).build();

            } else {
                ImageSource imageSource = ImageSource.newBuilder().setImageUri(imageUri).build();

                return Image.newBuilder().setSource(imageSource).build();
            }
        } catch (IOException e) {
            log.error("Error reading file");
            System.out.println("Error reading file");
            e.printStackTrace();
        }
        return null;
    }

    @Async("taskExecutor")
    public CompletableFuture<List<AnnotateImageResponse>> getImageAnnotations(String imagePath, String imageUri) {

        log.info("Getting image annotations");

        List<AnnotateImageResponse> responses = null;

        try (ImageAnnotatorClient visionClient = ImageAnnotatorClient.create()) {

            List<AnnotateImageRequest> requests = new ArrayList<>();

            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            Image img = getImage(imagePath, imageUri);
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
            requests.add(request);

            BatchAnnotateImagesResponse response = visionClient.batchAnnotateImages(requests);
            responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error getting response with error: %s%n", res.getError().getMessage());
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    annotation
                            .getAllFields()
                            .forEach((k, v) -> System.out.format("%s : %s%n", k, v.toString()));
                }
            }

            // TODO split into two methods one for uri and one for file path?

        } catch (IOException e) {
            log.error("Error reading file");
            System.out.println("Error reading file");
            e.printStackTrace();
        }

        return CompletableFuture.completedFuture(responses);
    }

}