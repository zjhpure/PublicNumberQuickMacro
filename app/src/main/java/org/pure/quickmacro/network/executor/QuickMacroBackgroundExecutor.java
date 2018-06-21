package org.pure.quickmacro.network.executor;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import java.util.concurrent.ExecutorService;

import retrofit.RetrofitError;

public class QuickMacroBackgroundExecutor implements BackgroundExecutor {
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService mExecutorService;

    public QuickMacroBackgroundExecutor(ExecutorService executorService) {
        mExecutorService = executorService;
    }

    @Override
    public <R> void execute(NetworkCallRunnable<R> runnable) {
        mExecutorService.execute(new TraktNetworkRunner<>(runnable));
    }

    @Override
    public <R> void execute(BackgroundCallRunnable<R> runnable) {
        mExecutorService.execute(new BackgroundCallRunner<>(runnable));
    }

    class BackgroundCallRunner<R> implements Runnable {
        private final BackgroundCallRunnable<R> mBackgroundRunnable;

        BackgroundCallRunner(BackgroundCallRunnable<R> runnable) {
            mBackgroundRunnable = runnable;
        }

        @Override
        public final void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    mBackgroundRunnable.preExecute();
                }
            });
            R result = mBackgroundRunnable.runAsync();
            sHandler.post(new ResultCallback(result));
        }

        private class ResultCallback implements Runnable {
            private final R mResult;

            private ResultCallback(R result) {
                mResult = result;
            }

            @Override
            public void run() {
                mBackgroundRunnable.postExecute(mResult);
            }
        }
    }

    class TraktNetworkRunner<R> implements Runnable {
        private final NetworkCallRunnable<R> mBackgroundRunnable;

        TraktNetworkRunner(NetworkCallRunnable<R> runnable) {
            mBackgroundRunnable = runnable;
        }

        @Override
        public final void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    mBackgroundRunnable.onPreTraktCall();
                }
            });
            R result = null;
            RetrofitError retrofitError = null;
            try {
                result = mBackgroundRunnable.doBackgroundCall();
            } catch (RetrofitError re) {
                retrofitError = re;
            }
            sHandler.post(new ResultCallback(result, retrofitError));
        }

        private class ResultCallback implements Runnable {
            private final R mResult;
            private final RetrofitError mRetrofitError;

            private ResultCallback(R result, RetrofitError retrofitError) {
                mResult = result;
                mRetrofitError = retrofitError;
            }

            @Override
            public void run() {
                if (mResult != null) {
                    mBackgroundRunnable.onSuccess(mResult);
                } else if (mRetrofitError != null) {
                    mBackgroundRunnable.onError(mRetrofitError);
                }
                mBackgroundRunnable.onFinished();
            }
        }
    }
}
