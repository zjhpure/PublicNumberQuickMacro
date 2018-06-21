package org.pure.quickmacro.network.retrofit;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.pure.quickmacro.constant.Const;

/**
 * OkHttp工具,创建OKHttpClient
 */
class OkHttpUtils {
    private static OkHttpClient singleton;

    static OkHttpClient getInstance(Context context) {
        if (singleton == null) {
            synchronized (OkHttpUtils.class) {
                if (singleton == null) {
                    singleton = new OkHttpClient();
                    if (context != null) {
                        try {
                            File cacheDir = new File(context.getCacheDir(), UUID.randomUUID().toString());
                            Cache cache = new Cache(cacheDir, 1024);
                            singleton.setCache(cache);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    singleton.setConnectTimeout(Const.CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                    singleton.setReadTimeout(Const.READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                }
            }
        }
        return singleton;
    }
}
