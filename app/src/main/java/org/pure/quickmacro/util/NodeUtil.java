package org.pure.quickmacro.util;

import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 点击工具
 */
public class NodeUtil {
    private static final String LOG_TAG = "NodeUtil";
    private static final int millis = 1000;

    /**
     * 按下某个节点
     */
    public static boolean performClick(AccessibilityNodeInfo clickNode) {
        while (clickNode != null && !clickNode.isClickable()) {
            clickNode = clickNode.getParent();
        }
        if (clickNode != null) {
            boolean result = false;
            try {
                result = clickNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Thread.sleep(millis);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        Log.e(LOG_TAG, "clickNode is null");
        return false;
    }
//
//    /**
//     * 上滑操作
//     */
//    public static boolean performScroller(AccessibilityNodeInfo scrollerNode) {
//        while (scrollerNode != null && !scrollerNode.isScrollable()) {
//            scrollerNode = scrollerNode.getParent();
//        }
//        if (scrollerNode != null) {
//            boolean result = false;
//            try {
//                result = scrollerNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
//                Thread.sleep(millis);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//        Log.e(LOG_TAG, "scrollerNode is null");
//        return false;
//    }
//
//    /**
//     * 下滑操作
//     */
//    public static boolean performBackScroller(AccessibilityNodeInfo scrollerNode) {
//        while (scrollerNode != null && !scrollerNode.isScrollable()) {
//            scrollerNode = scrollerNode.getParent();
//        }
//        if (scrollerNode != null) {
//            boolean result = false;
//            try {
//                result = scrollerNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
//                Thread.sleep(millis);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//        Log.e(LOG_TAG, "scrollerNode is null");
//        return false;
//    }

    /**
     * 寻找节点并点击,根据文字
     */
    public static boolean findNodeByTextAndPerformClick(AccessibilityNodeInfo root, String text) {
        if (root == null)
            return false;
        List<AccessibilityNodeInfo> txtNodeInfoList = root.findAccessibilityNodeInfosByText(text);
        if (txtNodeInfoList == null || txtNodeInfoList.isEmpty()) {
            Log.e(LOG_TAG, "没有找到" + text + "按钮");
            return false;
        }
        AccessibilityNodeInfo clickNode = null;
        for (AccessibilityNodeInfo nodeInfo : txtNodeInfoList) {
            if (nodeInfo.getText() != null && text.equals(nodeInfo.getText().toString())) {
                clickNode = nodeInfo;
                Log.i(LOG_TAG, "text= " + nodeInfo.getText());
            }
        }
        while (clickNode != null && !clickNode.isClickable()) {
            clickNode = clickNode.getParent();
        }
        if (clickNode != null) {
            boolean result = false;
            try {
                result = clickNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Thread.sleep(millis);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        Log.e(LOG_TAG, "clickNode is null");
        return false;
    }

    /**
     * 寻找节点并点击,根据id
     */
    public static boolean findNodeByIdAndPerformClick(AccessibilityNodeInfo root, String id) {
        List<AccessibilityNodeInfo> idNodeInfoList = root.findAccessibilityNodeInfosByViewId(id);
        if (idNodeInfoList == null || idNodeInfoList.isEmpty()) {
            return false;
        }
        // 根据id来找,找到list中的第一个就是目标node
        AccessibilityNodeInfo clickNode = null;
        for (AccessibilityNodeInfo info : idNodeInfoList) {
            if (info.getText() != null) {
                Log.i(LOG_TAG, "该id = " + info.getText());
            }
            if (info.getClassName() != null) {
                if (info.getClassName().toString().equals("android.widget.TextView")) {
                    clickNode = info;
                }
            }
        }
        if (clickNode == null) {
            clickNode = idNodeInfoList.get(0);
        }
        while (clickNode != null && !clickNode.isClickable()) {
            clickNode = clickNode.getParent();
        }
        if (clickNode != null) {
            boolean result = false;
            try {
                result = clickNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Thread.sleep(millis);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        Log.e(LOG_TAG, "clickNode is null");
        return false;
    }

//    /**
//     * 寻找节点,根据类名
//     */
//    public static AccessibilityNodeInfo findNodeByClass(AccessibilityNodeInfo root, String className) {
//        if (TextUtils.isEmpty(className) || root == null) {
//            return null;
//        }
//        int childCount = root.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            AccessibilityNodeInfo rootChild = root.getChild(i);
//            if (rootChild != null) {
//                if (className.equals(rootChild.getClassName().toString().trim())) {
//                    return rootChild;
//                }
//            }
//        }
//        return null;
//    }

    /**
     * 在列表中寻找节点,根据类名
     */
    public static List<AccessibilityNodeInfo> findNodeByClassList(AccessibilityNodeInfo root, String className) {
        List<AccessibilityNodeInfo> list = new ArrayList<>();
        if (TextUtils.isEmpty(className) || root == null) {
            return null;
        }
        int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo rootChild = root.getChild(i);
            if (rootChild != null) {
                if (className.equals(rootChild.getClassName().toString().trim())) {
                    list.add(rootChild);
                }
            }
        }
        Log.i(LOG_TAG, "长度:" + list.size());
        return list;
    }

//    /**
//     * 寻找节点,根据类名和寻找类名的次数
//     */
//    public static AccessibilityNodeInfo findNodeByClassNameAndTime(AccessibilityNodeInfo node, String className, int findClassTimes) {
//        List<AccessibilityNodeInfo> list = new ArrayList<>();
//        traverseNodeClassToList(node, list);
//        for (AccessibilityNodeInfo nodeInfo : list) {
//            if (nodeInfo.getClassName() != null && className.equals(nodeInfo.getClassName().toString())) {
//                findClassTimes--;
//                if (findClassTimes < 0) {
//                    findClassTimes = 0;
//                }
//                if (findClassTimes == 0)
//                    return nodeInfo;
//            }
//        }
//        return null;
//    }

//    /**
//     * 寻找View,通过id
//     */
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
//    public static AccessibilityNodeInfo findViewById(AccessibilityNodeInfo node, String id) {
//        if (node == null) {
//            return null;
//        }
//        List<AccessibilityNodeInfo> nodeInfoList = node.findAccessibilityNodeInfosByViewId(id);
//        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
//            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
//                if (nodeInfo != null) {
//                    return nodeInfo;
//                }
//            }
//        }
//        return null;
//    }

    /**
     * 寻找View,通过文本
     */
    public static AccessibilityNodeInfo findViewByText(AccessibilityNodeInfo node, String text, boolean clickable) {
        if (node == null) {
            return null;
        }
        List<AccessibilityNodeInfo> nodeInfoList = node.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            Log.i(LOG_TAG, "nodeInfoList size:" + nodeInfoList.size());
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                Log.i(LOG_TAG, "nodeInfo:" + nodeInfo);
                if (nodeInfo != null && (nodeInfo.isClickable() == clickable)) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }

//    /**
//     * 在列表中寻找节点,通过id
//     */
//    public static List<AccessibilityNodeInfo> findNodeByIdList(AccessibilityNodeInfo root, String id) {
//        return root.findAccessibilityNodeInfosByViewId(id);
//    }
//
//    /**
//     * 在列表中寻找节点,通过内容描述
//     */
//    public static List findByContentDescriptionList(AccessibilityNodeInfo node, String description) {
//        if (node == null) {
//            return null;
//        }
//        List<AccessibilityNodeInfo> list = new ArrayList<>();
//        if (TextUtils.isEmpty(description)) {
//            return Collections.EMPTY_LIST;
//        }
//        List<AccessibilityNodeInfo> list2 = new ArrayList<>();
//        traverseNodeClassToList(node, list2);
//        for (AccessibilityNodeInfo nodeInfo : list2) {
//            if (nodeInfo.getClassName() != null && description.equals(nodeInfo.getContentDescription().toString())) {
//                list.add(nodeInfo);
//            }
//        }
//        return list;
//    }
//
//    /**
//     * 寻找节点,通过内容描述
//     */
//    public static AccessibilityNodeInfo findByContentDescription(AccessibilityNodeInfo node, String description) {
//        if (node == null) {
//            return null;
//        }
//        if (TextUtils.isEmpty(description)) {
//            return null;
//        }
//        List<AccessibilityNodeInfo> list2 = new ArrayList<>();
//        traverseNodeClassToList(node, list2);
//        for (AccessibilityNodeInfo nodeInfo : list2) {
//            if (nodeInfo.getContentDescription() != null && description.equals(nodeInfo.getContentDescription().toString())) {
//                return nodeInfo;
//            }
//        }
//        return null;
//    }
//
//    public static List<AccessibilityNodeInfo> traverseNodeByClassList(AccessibilityNodeInfo root, String className) {
//        List<AccessibilityNodeInfo> list = new ArrayList<>();
//        if (TextUtils.isEmpty(className) || root == null) {
//            return null;
//        }
//        List<AccessibilityNodeInfo> list2 = new ArrayList<>();
//        traverseNodeClassToList(root, list2);
//        for (AccessibilityNodeInfo nodeInfo : list2) {
//            if (nodeInfo.getClassName() != null && className.equals(nodeInfo.getClassName().toString())) {
//                list.add(nodeInfo);
//            }
//        }
//        return list;
//    }

//    private static void traverseNodeClassToList(AccessibilityNodeInfo node, List<AccessibilityNodeInfo> list) {
//        if (node == null) {
//            return;
//        }
//        for (int i = 0; i < node.getChildCount(); i++) {
//            AccessibilityNodeInfo child = node.getChild(i);
//            if (child != null) {
//                list.add(child);
//                if (child.getChildCount() > 0) {
//                    traverseNodeClassToList(child, list);
//                }
//            }
//        }
//    }
}
