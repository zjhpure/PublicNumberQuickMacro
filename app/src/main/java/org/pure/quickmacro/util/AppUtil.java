package org.pure.quickmacro.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

import org.pure.quickmacro.MyApplication;
import org.pure.quickmacro.service.AutoClickPublicNumberService;

/**
 * App工具
 */
public class AppUtil {
    /**
     * 按键精灵是否开启了辅助功能
     */
    public static boolean isStartAccessibility() {
        int ok = 0;
        try {
            ok = Settings.Secure.getInt(MyApplication.getApplication().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        TextUtils.SimpleStringSplitter ms = new TextUtils.SimpleStringSplitter(':');
        if (ok == 1) {
            String settingValue = Settings.Secure.getString(MyApplication.getApplication().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                ms.setString(settingValue);
                while (ms.hasNext()) {
                    String accessibilityService = ms.next();
                    if (accessibilityService.contains(AutoClickPublicNumberService.class.getSimpleName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 跳转到设置
     */
    public static void jumpToSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转到微信
     */
    public static void jumpToWeChat(Context context) {
        ComponentName name = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        try {
            Intent intent = new Intent();
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(name);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.showToast(context, "亲，你安装微信了吗？");
            e.printStackTrace();
        }
    }
}
