package org.pure.quickmacro.controller;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.jsoup.helper.StringUtil;
import org.pure.quickmacro.MyApplication;
import org.pure.quickmacro.service.AutoClickPublicNumberService;
import org.pure.quickmacro.util.NodeUtil;
import org.pure.quickmacro.util.PreferenceUtil;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * @author pure
 * @date 18-6-19 下午7:28
 * @description 自动点击公众号历史消息
 */
@SuppressLint("LongLogTag")
public class ClickPublicNumberController extends ClickPublicNumberBaseController implements ClickPublicNumberInterface {
    private static final String LOG_TAG = "ClickPublicNumberController";
    private Boolean isExit = false;

    public ClickPublicNumberController(AutoClickPublicNumberService service) {
        super(service);
    }

    protected AccessibilityNodeInfo getRoot() {
        return mService.getRoot();
    }
    
    @Override
    public void doTask(AccessibilityEvent event) {
        Log.i(LOG_TAG, "doTask start");
        int mType = event.getEventType();
        String mClassName = (String) event.getClassName();
        // 在微信初始页面
        if (mType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && mClassName.equals(LauncherUI)) {
            // 点击微信按钮
            clickWechatBtn();
            // 点击搜索框按钮
            clickSearchBtn();
        // 在微信搜索页面
        } else if (!isExit && mType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && mClassName.equals(searchMainUI)) {
            // 处理搜索页面
            handleSearchPage();
        // 在微信公众号聊天页面
        } else if (mType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && mClassName.equals(chattingUI)) {
            // 点击人物按钮
            clickCharacterBtn();
        // 在微信公众号信息页面
        } else if (mType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && mClassName.equals(ContactInfoUI)) {
            // 点击查看历史消息按钮
            clickHistoryBtn();
        }
    }

    /**
     * 点击微信按钮
     */
    private void clickWechatBtn() {
        try {
            Log.i(LOG_TAG, "点击微信按钮");
            if (NodeUtil.findNodeByTextAndPerformClick(getRoot(), "微信")) {
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击搜索框按钮
     */
    private void clickSearchBtn() {
        try {
            Log.i(LOG_TAG, "点击搜索框按钮");
            List<AccessibilityNodeInfo> textViewList = NodeUtil.findNodeByClassList(getRoot(), "android.widget.TextView");
            if (textViewList != null ) {
                for (AccessibilityNodeInfo tempNodeInfo : textViewList) {
                    CharSequence contentDescription = tempNodeInfo.getContentDescription();
                    if (contentDescription != null && contentDescription.equals("搜索")) {
                        if (NodeUtil.performClick(tempNodeInfo)) {
                            Log.i(LOG_TAG, "点击搜索框按钮成功");
                            Thread.sleep(1000);
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理搜索页面
     */
    private void handleSearchPage() {
        try {
            Log.i(LOG_TAG, "处理搜索页面");
            // 获取公众号名称
            String publicNumberName = getPublicNumberName();
            if (StringUtil.isBlank(publicNumberName)) {
                Log.i(LOG_TAG, "公众号名称为空");
                // 公众号名称为空,两次返回,第一次退出输入法,第二次退出搜索页面)
                if (mService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)) {
                    Thread.sleep(1000);
                    if (mService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)) {
                        Log.i(LOG_TAG, "退出搜索页面成功");
                    }
                }
            } else {
                Log.i(LOG_TAG, "公众号名称为:" + publicNumberName);
                // 粘贴公众号名称到搜索框
                pastePublicNumberNameToSearchBox(publicNumberName);
                // 查找搜索结果按钮,clickable参数要为false,因为还有个EditText(搜索框处)也是同样名称的,但它的clickable为true,
                // 这里要找的是TextView,clickable是为false的
//                AccessibilityNodeInfo searchResult = NodeUtil.findViewByText(getRoot(), publicNumberName, true);
                AccessibilityNodeInfo searchResult = NodeUtil.findViewByText(getRoot(), publicNumberName, false);
                Log.i(LOG_TAG, "searchResult:" + searchResult);
//                // 获取搜索结果按钮的父层
//                AccessibilityNodeInfo searchResultParent = searchResult.getParent();
//                Log.i(LOG_TAG, "searchResultParent:" + searchResultParent);
//                // 点击搜索结果按钮的父层
//                if (NodeUtil.performClick(searchResultParent)) {
                // 点击搜索结果按钮
                if (NodeUtil.performClick(searchResult)) {
                    isExit = true;
                    Log.i(LOG_TAG, "isExit is set to true");
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击人物按钮
     */
    private void clickCharacterBtn() {
        try {
            Log.i(LOG_TAG, "点击人物按钮");
            // 微信版本不同id会不同,可通过Android Device Monitor查询id值
            if (NodeUtil.findNodeByIdAndPerformClick(getRoot(), "com.tencent.mm:id/hi")) {
                Log.i(LOG_TAG, "点击人物按钮成功");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击查看历史消息按钮
     */
    private void clickHistoryBtn() {
        try {
            Log.i(LOG_TAG, "点击查看历史消息按钮");
            if (NodeUtil.findNodeByTextAndPerformClick(getRoot(), "查看历史消息")) {
                Log.i(LOG_TAG, "点击查看历史消息按钮成功");
                Thread.sleep(5000);
                // 退出到微信初始页面,四次返回
                if (mService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)) {
                    Thread.sleep(200);
                    if (mService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)) {
                        Thread.sleep(200);
                        if (mService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)) {
                            Thread.sleep(200);
                            if (mService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)) {
                                MyApplication.setIsNeedToRequest(true);
                                Log.i(LOG_TAG, "isNeedToRequest is set to true");
                                isExit = false;
                                Log.i(LOG_TAG, "isExit is set to false");
                                Thread.sleep(200);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 粘贴公众号名称到搜索框
     */
    @SuppressLint("ObsoleteSdkInt")
    private void pastePublicNumberNameToSearchBox(String publicNumberName) {
        // 获取输入焦点
        AccessibilityNodeInfo target = getRoot().findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
        if (target != null) {
            // 获取粘贴板服务
            ClipboardManager clipboard = (ClipboardManager) mService.getSystemService(CLIPBOARD_SERVICE);
            if (clipboard != null) {
                ClipData clip = ClipData.newPlainText("label", publicNumberName);
                clipboard.setPrimaryClip(clip);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    // 按下粘贴键
                    target.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                    try {
                        // 这里必须休眠够1s,根据实际可能还需延长,0.2s不够,
                        // 如果休眠时间太短,搜索结果还没出来就执行点击公众号了,
                        // 那检测到的页面只会是搜索结果还没出之前的页面,这样是找不到公众号的,
                        // 休眠时间要足够搜索结果弹出来
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i(LOG_TAG, "在搜索框输入了:" + publicNumberName);
                }
            }
        }
    }

    /**
     * 获取公众号名称
     */
    private String getPublicNumberName() {
        String publicNumberName = PreferenceUtil.getString("publicNumberName", "");
        Log.i(LOG_TAG, "publicNumberName:" + publicNumberName);
        if (StringUtil.isBlank(publicNumberName)) {
            Log.i(LOG_TAG, "公众号的名称为空");
        }
        return publicNumberName;
    }
}
