package io.scribeapp.connection;

import android.util.Log;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

public class APIErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        Log.e("RETROFIT", cause.getMessage());
        return null;
    }
}
