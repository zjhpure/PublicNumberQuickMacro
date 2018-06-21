package org.pure.quickmacro.common;

import java.util.ArrayList;
import java.util.List;

import org.pure.quickmacro.R;
import org.pure.quickmacro.model.FunctionItem;

/**
 * @author pure
 * @date 18-6-19 下午7:27
 * @description 功能工厂
 */
public class FunctionItemFactory {
    private static FunctionItemFactory instance;

    private FunctionItemFactory() {

    }

    public static FunctionItemFactory getInstance() {
        synchronized (FunctionItemFactory.class) {
            if (instance == null) {
                instance = new FunctionItemFactory();
            }
        }
        return instance;
    }

    public List<FunctionItem> createFuncItems() {
        return new ArrayList<FunctionItem>() {
            {
                add(new FunctionItem("启动", "启动按键精灵", R.drawable.icon_start));
                add(new FunctionItem("配置", "设置服务器", R.drawable.icon_set));
            }
        };
    }
}