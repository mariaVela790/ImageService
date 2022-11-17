package com.imageservice.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisionService {

    // TODO be sure to call "close" on the imageClient when done

    private Image getImage(MultipartFile image, String imageUri) {
        try {
            if (image != null) {
                byte[] imgBytes = StreamUtils.copyToByteArray(image.getInputStream());
                ByteString imgByteString = ByteString.copyFrom(imgBytes);

                return Image.newBuilder().setContent(imgByteString).build();

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
    public CompletableFuture<List<AnnotateImageResponse>> getImageAnnotations(MultipartFile image, String imageUri) {
        log.info("Getting image annotations");
        // TODO read multipartfile as a ByteString?
        //        TODO if the above option doesn't work, save file to local directory?
        //        TODO for file uris pull the file?
        // TODO separate methods one to handle uris and one to handle files

        List<AnnotateImageResponse> responses = null;
        try (ImageAnnotatorClient visionClient = ImageAnnotatorClient.create()) {

            List<AnnotateImageRequest> requests = new ArrayList<>();

            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            Image img = getImage(image, imageUri);

            AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();

            requests.add(request);

            BatchAnnotateImagesResponse response = visionClient.batchAnnotateImages(requests);
            responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    //                  log.error("Error getting response with error: %s", res.getError().getMessage());
                    System.out.format("Error getting response with error: %s%n", res.getError().getMessage());
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    annotation
                            .getAllFields()
                            .forEach((k, v) -> System.out.format("%s : %s%n", k, v.toString()));
                }
            }


        } catch (IOException e) {
            log.error("Error reading file");
            System.out.println("Error reading file");
            e.printStackTrace();
        }

        return CompletableFuture.completedFuture(responses);
    }

}