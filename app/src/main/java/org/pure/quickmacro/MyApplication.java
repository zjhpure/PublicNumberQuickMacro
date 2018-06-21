package org.pure.quickmacro;

import android.app.Application;

import java.util.concurrent.Executors;

import org.pure.quickmacro.network.executor.BackgroundExecutor;
import org.pure.quickmacro.network.executor.QuickMacroBackgroundExecutor;

/**
 * @author pure
 * @date 18-6-7 下午6:05
 * @description App类
 */
public class MyApplication extends Application {
    @SuppressWarnings("StaticFieldLeak")
    private static Application application;
    private static Boolean isNeedToRequest = true;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application getApplication() {
        return application;
    }

    public static BackgroundExecutor getBackgroundExecutor() {
        final int numberCores = Runtime.getRuntime().availableProcessors();
        return new QuickMacroBackgroundExecutor(Executors.newFixedThreadPool(numberCores * 2 + 1));
    }

    public static Boolean getIsNeedToRequest() {
        return isNeedToRequest;
    }

    public static void setIsNeedToRequest(Boolean isNeedToRequest) {
        MyApplication.isNeedToRequest = isNeedToRequest;
    }
}
