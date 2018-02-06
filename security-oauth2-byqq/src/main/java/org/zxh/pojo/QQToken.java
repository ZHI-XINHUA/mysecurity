package org.zxh.pojo;

/**
 * Created by xh.zhi on 2018-2-6.
 * 根据授权获取的授权令牌
 */
public class QQToken {
    /**
     * token
     */
    private String accessToken;

    /**
     * 有效期
     */
    private int expiresIn;

    /**
     * 刷新时用的 token
     */
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
