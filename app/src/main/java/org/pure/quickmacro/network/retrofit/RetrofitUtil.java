package org.pure.quickmacro.network.retrofit;

import android.content.Context;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.Converter;

/**
 * Retrofit工具类
 */
public class RetrofitUtil {
    public static <T> T createApi(Context context, Class<T> clazz, String URL, Converter converter) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(URL);
        builder.setConverter(converter);
        builder.setClient(new OkClient(OkHttpUtils.getInstance(context)));
        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        return builder.build().create(clazz);
    }
}