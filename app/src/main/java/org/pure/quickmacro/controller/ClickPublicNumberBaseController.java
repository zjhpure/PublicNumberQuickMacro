package org.pure.quickmacro.controller;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.pure.quickmacro.service.AutoClickPublicNumberService;

/**
 * @author pure
 * @date 18-6-19 下午8:31
 * @description 自动点击公众号历史消息基类
 */
public class ClickPublicNumberBaseController implements ClickPublicNumberInterface {
    static final String ContactInfoUI = "com.tencent.mm.plugin.profile.ui.ContactInfoUI";
    static final String LauncherUI = "com.tencent.mm.ui.LauncherUI";
    static final String searchMainUI = "com.tencent.mm.plugin.fts.ui.FTSMainUI";
    static final String chattingUI = "com.tencent.mm.ui.chatting.ChattingUI";
    AutoClickPublicNumberService mService;

    ClickPublicNumberBaseController(AutoClickPublicNumberService service) {
        this.mService = service;
    }

    @Override
    public void doTask(AccessibilityEvent event) {

    }

    protected AccessibilityNodeInfo getRoot() {
        return mService.getRoot();
    }
}
