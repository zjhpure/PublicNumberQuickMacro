package org.pure.quickmacro.controller;

import android.view.accessibility.AccessibilityEvent;

/**
 * @author pure
 * @date 18-6-19 下午8:35
 * @description 自动点击公众号历史消息接口
 */
public interface ClickPublicNumberInterface {
    /**
     * 执行自动化事件
     */
    void doTask(AccessibilityEvent event);
}
