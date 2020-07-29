package io.nuite.modules.online_sales_app.resolver;

import io.nuite.modules.online_sales_app.annotation.XcxCustom;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.interceptor.XcxAuthorizationInterceptor;
import io.nuite.modules.online_sales_app.service.CustomerUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class XcxCustomHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private CustomerUserInfoService customerUserInfoService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(CustomerUserInfo.class) && parameter.hasParameterAnnotation(XcxCustom.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest request, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(XcxAuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (object == null) {
            return null;
        }

        //获取用户信息
        CustomerUserInfo user = customerUserInfoService.selectById((Long) object);

        return user;
    }
}
