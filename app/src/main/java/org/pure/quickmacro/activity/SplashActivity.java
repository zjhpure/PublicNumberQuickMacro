package org.pure.quickmacro.activity;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import org.pure.quickmacro.R;

/**
 * @author pure
 * @date 18-6-19 下午5:35
 * @description 启动activity
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void setContentView() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initData() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                redirect(MainActivity.class);
                finish();
            }
        }, 1500);
    }

    /**
     * 防止用户返回键或home键退出APP
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) || super.onKeyDown(keyCode, event);
    }
}
