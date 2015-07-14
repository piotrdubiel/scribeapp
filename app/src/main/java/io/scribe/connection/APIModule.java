package io.scribe.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ws.WebSocketCall;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.scribe.classifier.model.ClassificationRequestSerializer;
import io.scribe.classifier.model.GestureClassificationRequest;
import io.scribe.classifier.model.VectorClassificationRequest;
import io.scribe.rx.websocket.WebsocketObservable;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import rx.Observable;

@Module
public class ApiModule {
    //private final static String API_URL = "http://scribe-server.herokuapp.com";
    public static final String API_DOMAIN = "192.168.1.105:5000";
    private final static String API_URL = "http://" + API_DOMAIN;
    public final static String WEBSOCKET_URL = "ws://" + API_DOMAIN + "/ws/recognize";
//    private final static String WEBSOCKET_URL = "ws://echo.websocket.org";

    @Provides
    @Singleton
    ServiceConnector providesServiceConnector(HerokuConnector connector) {
        return connector;
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(1, TimeUnit.SECONDS);
        client.setWriteTimeout(1, TimeUnit.SECONDS);
        client.setReadTimeout(1, TimeUnit.SECONDS);
        return client;
    }

    @Provides
    @Singleton
    Client providesClient(OkHttpClient okHttpClient) {
        return new OkClient(okHttpClient);
    }

    @Provides
    @Singleton
    Endpoint providesEndpoint() {
        return Endpoints.newFixedEndpoint(API_URL);
    }

    @Provides
    @Singleton
    Gson providesGson() {
        return new GsonBuilder()
                .registerTypeAdapter(GestureClassificationRequest.class, new ClassificationRequestSerializer())
                .registerTypeAdapter(VectorClassificationRequest.class, new ClassificationRequestSerializer())
                .create();
    }

    @Provides
    @Singleton
    Converter providesConverter(Gson gson) {
        return new GsonConverter(gson);
    }

    @Provides
    @Singleton
    ErrorHandler providesErrorHandler() {
        return new ApiErrorHandler();
    }

    @Provides
    @Singleton
    RestAdapter providesRestAdapter(Client client, Endpoint endpoint, Converter converter, ErrorHandler errorHandler) {
        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .setConverter(converter)
                .build();
    }

    @Provides
    @Singleton
    ApiService providesApiService(RestAdapter restAdapter) {
        return restAdapter.create(ApiService.class);
    }

    @Provides
    @Websocket
    Observable<String> providesWebsocketObservable(OkHttpClient client) {
        Request request = new Request.Builder()
                .url(WEBSOCKET_URL)
                .build();
        return WebsocketObservable.connect(WebSocketCall.create(client, request));
    }
}
