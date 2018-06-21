package org.pure.quickmacro.network.retrofit;

import java.util.Map;

import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

import org.pure.quickmacro.network.result.CommonResult;
import org.pure.quickmacro.model.PublicNumberModel;

/**
 * @author pure
 * @date 18-6-7 下午6:05
 * @description 服务
 */
public interface Service {
    /**
     * 获取要点击的公众号接口
     */
    @POST("/crawler/public_number/get_click_public_number")
    @FormUrlEncoded
    CommonResult<PublicNumberModel> getClickPublicNumber(@FieldMap Map<String, String> map);
}
