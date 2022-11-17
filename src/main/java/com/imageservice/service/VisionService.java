package com.imageservice.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.imageservice.model.ImageFile;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisionService {

    // TODO be sure to call "close" on the imageClient when done

//    @Async("taskExecutor")
//    public CompletableFuture<List<AnnotateImageResponse>> getImageAnnotations(String filePath) {
//      log.info("Getting image annotations");
////      String filePath = "src/main/resources/coffee.jpg";
//
//      // TODO read multipartfile as a ByteString?
////        TODO if the above option doesn't work, save file to local directory?
////        TODO for file uris pull the file?
//      // TODO separate methods one to handle uris and one to handle files
//
//
//      Path path = Paths.get(filePath);
//
//      List<AnnotateImageResponse> responses = null;
//      try (ImageAnnotatorClient imageClient = ImageAnnotatorClient.create()) {
//          byte[] data = Files.readAllBytes(path);
//          ByteString imgBytes = ByteString.copyFrom(data);
//
//          List<AnnotateImageRequest> requests = new ArrayList<>();
//
//          Image img = Image.newBuilder().setContent(imgBytes).build();
//          Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
//          AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
//
//          requests.add(request);
//
//          BatchAnnotateImagesResponse response = imageClient.batchAnnotateImages(requests);
//          responses = response.getResponsesList();
//
//          for (AnnotateImageResponse res : responses) {
//              if (res.hasError()) {
////                  log.error("Error getting response with error: %s", res.getError().getMessage());
//                  System.out.format("Error getting response with error: %s%n", res.getError().getMessage());
//              }
//
//              for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
//                  annotation
//                          .getAllFields()
//                          .forEach((k, v) -> System.out.format("%s : %s%n", k, v.toString()));
//              }
//          }
//
//
//      } catch (IOException e) {
//          log.error("Error reading file");
//          System.out.println("Error reading file");
//          e.printStackTrace();
//      }
//
//      return CompletableFuture.completedFuture(responses);
//    }
//}
//

    @Async("taskExecutor")
    public CompletableFuture<List<AnnotateImageResponse>> getImageAnnotations(MultipartFile file) {
        log.info("Getting image annotations");
    //      String filePath = "src/main/resources/coffee.jpg";

        // TODO read multipartfile as a ByteString?
    //        TODO if the above option doesn't work, save file to local directory?
    //        TODO for file uris pull the file?
        // TODO separate methods one to handle uris and one to handle files


//        Path path = Paths.get(filePath);

        List<AnnotateImageResponse> responses = null;
        try (ImageAnnotatorClient imageClient = ImageAnnotatorClient.create()) {
            byte[] imgBytes = StreamUtils.copyToByteArray(file.getInputStream());
            ByteString imgByteString = ByteString.copyFrom(imgBytes);

            List<AnnotateImageRequest> requests = new ArrayList<>();

            Image img = Image.newBuilder().setContent(imgByteString).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();

            requests.add(request);

            BatchAnnotateImagesResponse response = imageClient.batchAnnotateImages(requests);
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