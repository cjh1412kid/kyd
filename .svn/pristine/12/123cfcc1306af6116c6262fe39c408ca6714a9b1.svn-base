package com.nuite.oauth2;

import com.alibaba.fastjson.JSON;
import com.nuite.common.utils.HttpContextUtils;
import com.nuite.common.utils.Result;
import com.nuite.common.utils.ResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oauth2过滤器
 */
@Slf4j
public class OAuth2Filter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String key = getRequestToken((HttpServletRequest) request, "key");
        String time = getRequestToken((HttpServletRequest) request, "time");
        String sign = getRequestToken((HttpServletRequest) request, "sign");
        String data = getRequestData((HttpServletRequest) request);
        String path = getRequestUrl((HttpServletRequest) request);
        String[] urlPathAndVersion = path.split("/");
        String apiName = urlPathAndVersion[0];
        String apiVersion = urlPathAndVersion[1];

        return new OAuth2Token(key, time, sign, apiName, apiVersion, data);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

        //获取请求头信息key
        String key = getRequestToken((HttpServletRequest) request, "key");
        if (StringUtils.isBlank(key)) {
            log.info("请求用户key：" + key);
            String json = JSON.toJSONString(Result.error(ResultStatus.REQUIRED.value(), "缺少必填参数key"));
            httpResponse.getWriter().print(json);
            return false;
        }

        //获取请求头信息time
        String time = getRequestToken((HttpServletRequest) request, "time");
        if (StringUtils.isBlank(time)) {
            log.info("请求用户time：" + time);
            String json = JSON.toJSONString(Result.error(ResultStatus.REQUIRED.value(), "缺少必填参数time"));
            httpResponse.getWriter().print(json);
            return false;
        }

        //获取请求头信息sign
        String sign = getRequestToken((HttpServletRequest) request, "sign");
        if (StringUtils.isBlank(sign)) {
            log.info("请求用户sign：" + sign);
            String json = JSON.toJSONString(Result.error(ResultStatus.REQUIRED.value(), "缺少必填参数sign"));
            httpResponse.getWriter().print(json);
            return false;
        }

        String path = getRequestUrl((HttpServletRequest) request);
        log.info("请求用户url:" + path);
        String[] urlPathAndVersion = path.split("/");
        if (urlPathAndVersion.length != 2) {
            String json = JSON.toJSONString(Result.error(ResultStatus.DATA_FORMAT_ERROR.value(), "请求路径错误"));
            httpResponse.getWriter().print(json);
            return false;
        }

        /*try {
            //判断时间是否超过五分钟
            long timeLong = Long.valueOf(time);
            long currentTime = System.currentTimeMillis();
            if (currentTime - timeLong > (1000 * 60 * 5)) {
                String json = JSON.toJSONString(Result.error(ResultStatus.DATA_ERROR.value(), "请求已过期"));
                httpResponse.getWriter().print(json);
                return false;
            }
        } catch (NumberFormatException e) {
            String json = JSON.toJSONString(Result.error(ResultStatus.DATA_FORMAT_ERROR.value(), "时间格式错误"));
            httpResponse.getWriter().print(json);
            return false;
        }*/


        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Result r = Result.error(ResultStatus.USER_ERROR.value(), throwable.getMessage());

            String json = JSON.toJSONString(r);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {

        }

        return false;
    }

    /**
     * 获取请求的头信息
     * 请求head的key
     */
    private String getRequestToken(HttpServletRequest httpRequest, String key) {
        //从header中获取token
        String token = httpRequest.getHeader(key);

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(key);
        }

        return token == null ? token : token.trim();
    }

    private String getRequestUrl(HttpServletRequest httpRequest) {
        //请求路径分析
        String path = httpRequest.getServletPath().trim();
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    private String getRequestData(HttpServletRequest httpRequest) {
        String data = httpRequest.getParameter("data");
        if (StringUtils.isBlank(data)) {

        }
        if (data == null) {
            data = "{}";
        }
        return data;
    }
}
