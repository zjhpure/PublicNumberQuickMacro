package org.pure.quickmacro.network.result;

import com.google.gson.annotations.SerializedName;

/**
 * @author pure
 * @date 18-6-21 上午1:34
 * @description 网络请求共同结果
 */
@SuppressWarnings("unused")
public class CommonResult<T> {
    // 错误码(0--成功,非0--失败)
    @SerializedName("errcode")
    private Integer errCode;
    // 附加消息
    @SerializedName("msg")
    private String msg;
    // 返回的结果
    @SerializedName("result")
    private T result;

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
