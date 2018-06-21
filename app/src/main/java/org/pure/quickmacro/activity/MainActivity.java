package org.pure.quickmacro.activity;

import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import org.pure.quickmacro.MyApplication;
import org.pure.quickmacro.R;
import org.pure.quickmacro.adapter.FunctionAdapter;
import org.pure.quickmacro.common.FunctionItemFactory;
import org.pure.quickmacro.model.PublicNumberModel;
import org.pure.quickmacro.network.NetService;
import org.pure.quickmacro.network.Ret2S1pF1pListener;
import org.pure.quickmacro.util.AppUtil;
import org.pure.quickmacro.util.PreferenceUtil;

import butterknife.BindView;

/**
 * @author pure
 * @date 18-6-19 下午5:34
 * @description 主activity
 */
public class MainActivity extends BaseActivity {
    private static final String LOG_TAG = "MainActivity";
    @BindView(R.id.ll)
    FrameLayout mLlRootView;
    @BindView(R.id.bannerContainer)
    FrameLayout mFlBannerContainer;
    @BindView(R.id.ryv)
    RecyclerView mRecyclerView;
    private boolean mIsFirstJump = false;
    private FunctionAdapter mAdapter;
    private NetService mNetService;
    // 定时任务
    CountDownTimer countDownTimer = new CountDownTimer(Integer.MAX_VALUE, 10 * 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.i(LOG_TAG, "onTick start");
            if (MyApplication.getIsNeedToRequest()) {
                mNetService.getClickPublicNumberTask(new Ret2S1pF1pListener<PublicNumberModel, String>() {
                    @Override
                    public void onSuccess(PublicNumberModel publicNumberModel) {
                        Log.i(LOG_TAG, "请求点击公众号成功:" + publicNumberModel);
                        if (null != publicNumberModel) {
                            String publicNumberName = publicNumberModel.getPublicNumberName();
                            if (PreferenceUtil.putString("publicNumberName", publicNumberName)) {
                                Log.i(LOG_TAG, "获取点击公众号:" + publicNumberName);
                                toast("获取点击公众号:" + publicNumberName);
                                MyApplication.setIsNeedToRequest(false);
                                Log.i(LOG_TAG, "isNeedToRequest is set to false");
                            } else {
                                Log.i(LOG_TAG, "获取点击公众号失败");
                                toast("获取点击公众号失败");
                            }
                        } else {
                            Log.i(LOG_TAG, "获取到的点击公众号为空");
                            toast("获取到的点击公众号为空");
                        }
                    }

                    @Override
                    public void onFailed(String s) {
                        Log.e(LOG_TAG, "请求点击公众号失败:" + s);
                        toast("请求点击公众号失败");
                    }
                });
            } else {
                Log.e(LOG_TAG, "未到请求点击公众号");
                toast("未到请求点击公众号");
            }
            Log.i(LOG_TAG, "onTick end");
        }

        @Override
        public void onFinish() {
            Log.i(LOG_TAG, "onFinish");
        }
    };

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FunctionAdapter(this, FunctionItemFactory.getInstance().createFuncItems());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        initNetService();
    }

    private void initNetService() {
        if (mNetService == null) {
            mNetService = new NetService(MyApplication.getApplication(), MyApplication.getBackgroundExecutor());
        }
    }

    @Override
    protected void setListener() {
        mAdapter.setOnItemClickListener(new FunctionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        // 开始自动点击公众号历史消息
                        startAutoClick();
                        break;
                    case 1:
                        redirect(SetServerActivity.class);
                }
            }
        });
    }

    private void startAutoClick() {
        Log.i(LOG_TAG, "startAutoClick start");
        if (!AppUtil.isStartAccessibility()) {
            AppUtil.jumpToSetting(this);
            mIsFirstJump = true;
        } else {
            AppUtil.jumpToWeChat(this);
        }
        Log.i(LOG_TAG, "startAutoClick end");
    }

    @Override
    protected void onResume() {
        if (mIsFirstJump && AppUtil.isStartAccessibility()) {
            AppUtil.jumpToWeChat(this);
            mIsFirstJump = false;
            // 等跳到微信页面时再初始化服务器，再开始定时任务，避免过早开始定时任务使已获取到的公众号被覆盖掉
            mNetService.initServer();
            countDownTimer.start();
        }
        super.onResume();
    }
}