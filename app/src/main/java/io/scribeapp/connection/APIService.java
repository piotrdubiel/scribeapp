package io.scribeapp.connection;

import io.scribeapp.classifier.model.ClassificationRequest;
import io.scribeapp.classifier.model.ClassificationResult;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface APIService {
    @GET("/api/token")
    Session token(String username, String password);

    @POST("/api/recognize")
    ClassificationResult recognize(@Body ClassificationRequest request);
}
