package com.example.zhongchou.di;

import com.example.zhongchou.di.component.BaseAppComponent;
import com.example.zhongchou.utils.ComponentReflectionInjector;

/**
 * Created by caiyk on 2017/9/28.
 */

public class BaseAppManager {

    private static volatile BaseAppManager mInstance;

    private ComponentReflectionInjector<BaseAppComponent> mAppComponentReflectionInjector;

    private BaseAppManager() {
    }

    public static BaseAppManager getInstance() {
        if (mInstance == null) {
            synchronized (BaseAppManager.class) {
                if (mInstance == null) {
                    mInstance = new BaseAppManager();
                }
            }
        }
        return mInstance;
    }

    public void inject(Object obj) {
        if (mAppComponentReflectionInjector == null) {
            return;
        }
        mAppComponentReflectionInjector.inject(obj);
    }

    public void setAppComponentReflectionInjector(ComponentReflectionInjector<BaseAppComponent> appComponentComponentReflectionInjector) {
        this.mAppComponentReflectionInjector = appComponentComponentReflectionInjector;
    }

    public BaseAppComponent getBaseAppComponent() {
        return this.mAppComponentReflectionInjector.getComponent();
    }
}
