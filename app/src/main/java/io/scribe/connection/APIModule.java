package io.scribe.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.scribe.classifier.model.ClassificationRequestSerializer;
import io.scribe.classifier.model.GestureClassificationRequest;
import io.scribe.classifier.model.VectorClassificationRequest;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

@Module(
        library = true,
        complete = false
)
public class APIModule {
    //private final static String API_URL = "http://scribe-server.herokuapp.com";
    public static final String API_DOMAIN = "10.0.3.34:5000";
    private final static String API_URL = "http://" + API_DOMAIN;
    public final static String WEBSOCKET_URL = "ws://" + API_DOMAIN + "/ws/recognize";
//    private final static String WEBSOCKET_URL = "ws://echo.websocket.org";

    @Provides
    @Singleton
    ServiceConnector provideServiceConnector(HerokuConnector connector) {
        return connector;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(1, TimeUnit.SECONDS);
        client.setWriteTimeout(1, TimeUnit.SECONDS);
        client.setReadTimeout(1, TimeUnit.SECONDS);
        return client;
    }

    @Provides
    @Singleton
    Client provideClient(OkHttpClient okHttpClient) {
        return new OkClient(okHttpClient);
    }

    @Provides
    @Singleton
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(API_URL);
    }

    @Provides
    @Singleton
    Converter provideConverter() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GestureClassificationRequest.class, new ClassificationRequestSerializer())
                .registerTypeAdapter(VectorClassificationRequest.class, new ClassificationRequestSerializer())
                .create();
        return new GsonConverter(gson);
    }

    @Provides
    @Singleton
    ErrorHandler provideErrorHandler() {
        return new APIErrorHandler();
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(Client client, Endpoint endpoint, Converter converter, ErrorHandler errorHandler) {
        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .setConverter(converter)
                .build();
    }

    @Provides
    @Singleton
    APIService provideAPIService(RestAdapter restAdapter) {
        return restAdapter.create(APIService.class);
    }
}
