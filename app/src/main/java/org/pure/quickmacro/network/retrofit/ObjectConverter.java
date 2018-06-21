package org.pure.quickmacro.network.retrofit;

import com.google.gson.Gson;

import org.pure.quickmacro.util.IOUtil;

import java.io.InputStream;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * 网络响应转对象的转换器
 */
public class ObjectConverter implements Converter {
    private final Gson gson;
    // 是否进行解压缩操作
    private boolean mIsNeedUngzip;

    public ObjectConverter(Gson gson, boolean isNeedUngzip) {
        this.gson = gson;
        mIsNeedUngzip = isNeedUngzip;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        InputStream is;
        try {
            is = body.in();
            byte[] bytes = IOUtil.toByteArray(is);
            if (mIsNeedUngzip) {
                bytes = IOUtil.ungzip(bytes);
            }
            String string = new String(bytes, "utf-8");
            return gson.fromJson(string, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }
}