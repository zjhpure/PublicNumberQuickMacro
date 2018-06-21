package org.pure.quickmacro.network.executor;

import retrofit.RetrofitError;

public abstract class NetworkCallRunnable<R> {
    public void onPreTraktCall() {}

    public abstract R doBackgroundCall() throws RetrofitError;

    public abstract void onSuccess(R result);

    public abstract void onError(RetrofitError re);

    public void onFinished() {}
 }
