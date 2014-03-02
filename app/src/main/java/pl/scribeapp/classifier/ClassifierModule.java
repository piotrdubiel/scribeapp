package pl.scribeapp.classifier;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.scribeapp.classifier.meta.MetaClassifier;
import pl.scribeapp.classifier.remote.RemoteClassifier;

/**
 * Created by piotrekd on 12/29/13.
 */
@Module(library = true)
public class ClassifierModule {
    @Provides @Singleton
    ClassificationHandler provideClassificationHandler() {
        return new ClassificationHandler();
    }

    @Provides @Singleton
    RemoteClassifier provideRemoteClassifier() {
        return new RemoteClassifier();
    }

    @Provides @Singleton
    MetaClassifier provideMetaClassifier() {
        return new MetaClassifier();
    }
}
