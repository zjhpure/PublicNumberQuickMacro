package org.pure.quickmacro.model;

/**
 * @author pure
 * @date 18-6-21 上午5:12
 * @description 功能item
 */
public class FunctionItem {
    public String funcName;
    public String subFuncName;
    public int resId;

    public FunctionItem(String funcName, String subFuncName, int resId) {
        this.funcName = funcName;
        this.subFuncName = subFuncName;
        this.resId = resId;
    }
}