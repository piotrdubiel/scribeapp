package io.scribeapp.connection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by piotrekd on 12/29/13.
 */
@Module(
        library = true,
        complete = false
)
public class ConnectionModule {
    @Provides
    @Singleton
    ServiceConnector provideServiceConnector(HerokuConnector connector) {
        return connector;
    }
}
