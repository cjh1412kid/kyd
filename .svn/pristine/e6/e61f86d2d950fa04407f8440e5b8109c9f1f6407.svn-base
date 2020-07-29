package com.nuite.oauth2;

import com.nuite.modules.platform.entity.Platform;
import com.nuite.modules.platform.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 认证
 */
@Slf4j
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Platform platform = (Platform) principals.getPrimaryPrincipal();

        //用户权限列表
        Set<String> permsSet = new HashSet<>();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OAuth2Token authToken = (OAuth2Token) token;
        String key = authToken.getPrincipal();
        if (StringUtils.isBlank(key)) {
            log.info("调用时缺少平台参数key");
            throw new IncorrectCredentialsException("缺少平台key");
        }
        //根据key，查询平台信息
        Platform platform = shiroService.queryPlatformByKey(key);
        //key失效
        if (platform == null) {
            log.info("未查到对应key的平台信息");
            throw new IncorrectCredentialsException("平台key不存在或已失效");
        }

        String secret = platform.getPlatSecret();
        //key=$key&time=$time&apiName=$apiName&apiVersion=$apiVersion&data=$data
/*        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("key=").append(key)
                .append("&time=").append(authToken.getTime())
                .append("&apiName=").append(authToken.getApiName())
                .append("&apiVersion=").append(authToken.getApiVersion())
                .append("&data=").append(authToken.getData());
        log.info("sign data:" + stringBuilder.toString());*/

        //验证签名
        /*HttpRequestParam postParam = HttpRequestParam.newInstance()
            .put("key", key)
            .put("time", authToken.getTime());

        //md5加密，获得签名
        String sign = MD5Utils.sign(postParam, secret);

        if(!sign.equals(authToken.getSign())){
            log.info("签名不匹配");
            throw new IncorrectCredentialsException("签名不匹配");
        }*/

//        log.info("签名：{}  {}",sign,authToken.getSign());

        return new SimpleAuthenticationInfo(platform, key, getName());
    }
}
