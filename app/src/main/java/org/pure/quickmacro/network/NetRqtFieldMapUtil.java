package org.pure.quickmacro.network;

import java.util.Map;

import org.pure.quickmacro.network.request.NetRqtRequest;

class NetRqtFieldMapUtil {
    static Map<String, String> getCommonBlankMap() {
        NetRqtRequest<Object> netRqtRequest = new NetRqtRequest<>();
        netRqtRequest.setData(new Object());
        return netRqtRequest.getFieldMap();
    }
}
