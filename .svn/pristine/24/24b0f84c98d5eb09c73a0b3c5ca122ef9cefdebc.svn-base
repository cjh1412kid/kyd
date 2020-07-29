package com.nuite.oauth2;


import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 */
@Data
public class OAuth2Token implements AuthenticationToken {
    private static final long serialVersionUID = 1L;
    private String key;
    private String time;
    private String sign;
    private String apiName;
    private String apiVersion;
    private String data;

    OAuth2Token(String key, String time, String sign, String apiName, String apiVersion, String data) {
        this.key = key;
        this.time = time;
        this.sign = sign;
        this.apiName = apiName;
        this.apiVersion = apiVersion;
        this.data = data;
    }

    @Override
    public String getPrincipal() {
        return key;
    }

    @Override
    public Object getCredentials() {
        return key;
    }
}
