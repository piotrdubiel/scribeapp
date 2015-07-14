package io.scribe.app;

import android.app.Application;

public abstract class BaseApplication extends Application implements Navigator {
    protected ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * This initialization cannot be moved to Kotlin.
         * Compiler will fail with Unresolved reference error: Dagger_ApplicationComponent
         */
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
