package com.zt.baseapp.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by caiyk on 2017/2/18.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ApplicationContext {
}
