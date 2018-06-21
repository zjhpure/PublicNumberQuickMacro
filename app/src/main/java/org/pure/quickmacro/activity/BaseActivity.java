package org.pure.quickmacro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

import org.pure.quickmacro.util.ToastUtil;

/**
 * @author pure
 * @date 18-6-19 下午5:33
 * @description activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ButterKnife.bind(this);
        initView();
        initData();
        setListener();
    }

    protected abstract void setContentView();

    protected void initView() {

    }

    protected abstract void initData();

    protected void setListener() {

    }

    protected void toast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    protected void redirect(Class targetClass) {
        startActivity(new Intent(this, targetClass));
    }
}
