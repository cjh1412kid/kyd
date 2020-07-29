package com.nuite.modules.erp.controller;

import com.nuite.common.exception.SmartException;
import com.nuite.common.utils.SpringContextUtils;
import com.nuite.modules.erp.service.CommonErpService;
import com.nuite.modules.platform.entity.Platform;
import com.nuite.modules.platform.service.PlatformService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractController {
    @Autowired
    protected PlatformService platformService;

    public CommonErpService getErpService(HttpServletRequest request) {
        String key = request.getHeader("key");
        if (StringUtils.isBlank(key)) {
            key = request.getParameter("key");
        }

        if (StringUtils.isBlank(key)) {
            throw new SmartException("key 参数不存在!");
        }

        Platform platform = platformService.queryPlatformByKey(key);
        if (platform == null) {
            throw new SmartException("platform信息不存在!");
        }

        return SpringContextUtils.getBean(platform.getBeanName(), CommonErpService.class);
    }
}
