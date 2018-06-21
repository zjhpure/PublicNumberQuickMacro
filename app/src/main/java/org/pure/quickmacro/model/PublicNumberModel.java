package org.pure.quickmacro.model;

/**
 * @author pure
 * @date 18-6-20 上午12:20
 * @description 公众号信息
 */
@SuppressWarnings("unused")
public class PublicNumberModel {
    private String publicNumberName;
    private String publicNumberWechatId;
    private String publicNumberBiz;

    public String getPublicNumberName() {
        return publicNumberName;
    }

    public void setPublicNumberName(String publicNumberName) {
        this.publicNumberName = publicNumberName;
    }

    public String getPublicNumberWechatId() {
        return publicNumberWechatId;
    }

    public void setPublicNumberWechatId(String publicNumberWechatId) {
        this.publicNumberWechatId = publicNumberWechatId;
    }

    public String getPublicNumberBiz() {
        return publicNumberBiz;
    }

    public void setPublicNumberBiz(String publicNumberBiz) {
        this.publicNumberBiz = publicNumberBiz;
    }

    @Override
    public String toString() {
        return "PublicNumberModel("
                + "publicNumberName=" + publicNumberName + ", "
                + "publicNumberWechatId=" + publicNumberWechatId + ", "
                + "publicNumberBiz=" + publicNumberBiz + ")";
    }
}
