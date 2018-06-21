package org.pure.quickmacro.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import retrofit.RetrofitError;

import java.util.Map;

import org.pure.quickmacro.constant.Const;
import org.pure.quickmacro.network.executor.BackgroundExecutor;
import org.pure.quickmacro.network.executor.NetworkCallRunnable;
import org.pure.quickmacro.network.result.CommonResult;
import org.pure.quickmacro.network.retrofit.ObjectConverter;
import org.pure.quickmacro.network.retrofit.RetrofitUtil;
import org.pure.quickmacro.network.retrofit.Service;
import org.pure.quickmacro.util.PreferenceUtil;
import org.pure.quickmacro.model.PublicNumberModel;

/**
 * @author pure
 * @date 18-6-7 下午6:05
 * @description 网络服务
 */
public class NetService {
    private static final String LOG_TAG = "NetService";
    private Context mContext;
    private final BackgroundExecutor mExecutor;
    private Service mApiServer;

    public NetService(Context context, BackgroundExecutor executor) {
        mContext = context;
        mExecutor = executor;
    }

    public void initServer() {
        String api_host = PreferenceUtil.getString("API_HOST", "127.0.0.1:10000");
        mApiServer = RetrofitUtil.createApi(mContext, Service.class, api_host, new ObjectConverter(new Gson(), false));
        Log.i(LOG_TAG, "api_host:" + api_host);
    }

    public void getClickPublicNumberTask(final Ret2S1pF1pListener<PublicNumberModel, String> listener) {
        mExecutor.execute(new NetworkCallRunnable<CommonResult<PublicNumberModel>>() {
            @Override
            public void onPreTraktCall() {
            }

            @Override
            public CommonResult<PublicNumberModel> doBackgroundCall() throws RetrofitError {
                // 请求参数
                Map<String, String> map = NetRqtFieldMapUtil.getCommonBlankMap();
                return mApiServer.getClickPublicNumber(map);
            }

            @Override
            public void onSuccess(CommonResult<PublicNumberModel> result) {
                // 请求成功
                if (listener != null && result != null && result.getErrCode() == 0) {
                    listener.onSuccess(result.getResult());
                } else {
                    String msg = Const.DATA_EXCEPTION;
                    if (result != null) {
                        msg = result.getMsg();
                    }
                    if (listener != null) {
                        listener.onFailed(msg);
                    }
                }
            }

            @Override
            public void onError(RetrofitError re) {
                // 请求失败
                if (listener != null) {
                    listener.onFailed(Const.NET_EXCEPTION);
                }
            }

            @Override
            public void onFinished() {
            }
        });
    }
}
