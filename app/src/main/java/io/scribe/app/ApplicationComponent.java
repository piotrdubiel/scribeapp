package io.scribe.app;

import javax.inject.Singleton;

import dagger.Component;
import io.scribe.account.LoginActivity;
import io.scribe.classifier.WebsocketRecognitionHandler;
import io.scribe.input.MainInputService;
import io.scribe.account.AccountActivity;

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {
    void inject(BaseApplication application);
    void inject(AccountActivity accountActivity);
    void inject(LoginActivity accountActivity);
    void inject(MainInputService mainInputService);
    void inject(WebsocketRecognitionHandler handler);
}
