package io.scribeapp.classifier;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.scribeapp.app.ApplicationModule;
import io.scribeapp.classifier.meta.MetaClassifier;
import io.scribeapp.classifier.remote.RemoteClassifier;
import io.scribeapp.classifier.utils.Meta;
import io.scribeapp.classifier.utils.Remote;

/**
 * Created by piotrekd on 12/29/13.
 */
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
