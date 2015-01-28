package io.scribe.classifier;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.scribe.app.ApplicationModule;
import io.scribe.classifier.meta.MetaClassifier;
import io.scribe.classifier.remote.RemoteClassifier;
import io.scribe.classifier.utils.Meta;
import io.scribe.classifier.utils.Remote;

@Module(library = true, addsTo = ApplicationModule.class)
public class ClassifierModule {
    @Provides
    @Singleton
    ClassificationHandler provideClassificationHandler() {
        return new ClassificationHandler();
    }

    @Provides
    @Singleton
    @Remote
    Classifier provideRemoteClassifier(RemoteClassifier remoteClassifier) {
        return remoteClassifier;
    }

    @Provides
    @Singleton
    @Meta
    Classifier provideMetaClassifier(MetaClassifier metaClassifier) {
        return metaClassifier;
    }
}
