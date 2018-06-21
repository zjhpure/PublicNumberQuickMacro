package org.pure.quickmacro.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.pure.quickmacro.controller.ClickPublicNumberController;
import org.pure.quickmacro.controller.ClickPublicNumberInterface;

/**
 * @author pure
 * @date 18-6-19 下午9:15
 * @description 自动点击公众号历史消息服务
 */
@SuppressLint("LongLogTag")
public class AutoClickPublicNumberService extends AccessibilityService {
    private static final String LOG_TAG = "AutoClickPublicNumberService";
    private ClickPublicNumberInterface mController;

    /**
     * 辅助服务被绑定时触发该方法,可通常在这里做一些初始化操作
     */
    @Override
    protected void onServiceConnected() {
        Log.i(LOG_TAG, "onServiceConnected");
        // 自动点击公众号的历史消息
        mController = new ClickPublicNumberController(this);
    }

    /**
     * 回调函数,系统不断地调用此方法
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(LOG_TAG, "event=" + AccessibilityEvent.eventTypeToString(event.getEventType()));
        Log.i(LOG_TAG, "className=" + event.getClassName());
        if (mController != null) {
            // 不断地调用此方法
            mController.doTask(event);
        }
    }

    public AccessibilityNodeInfo getRoot() {
        return getRootInActiveWindow();
    }

    @Override
    public void onInterrupt() {
        Log.e(LOG_TAG, "onInterrupt");
    }

    @Override
    public void onDestroy() {
        Log.e(LOG_TAG, "onDestroy");
    }
}
