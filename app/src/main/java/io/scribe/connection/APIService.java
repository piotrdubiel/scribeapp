package io.scribe.connection;

import io.scribe.classifier.model.ClassificationRequest;
import io.scribe.classifier.model.ClassificationResult;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

public interface APIService {
    @GET("/api/token")
    Session token(String username, String password);

    @POST("/api/recognize")
    Observable<ClassificationResult> recognize(@Body ClassificationRequest request);
}
