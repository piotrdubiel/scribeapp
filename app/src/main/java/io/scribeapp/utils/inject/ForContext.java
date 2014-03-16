package io.scribeapp.utils.inject;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by piotrekd on 13/03/14.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ForContext {
}
