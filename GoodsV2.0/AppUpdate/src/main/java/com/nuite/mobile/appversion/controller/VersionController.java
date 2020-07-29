package com.nuite.mobile.appversion.controller;

import com.nuite.mobile.appversion.service.VersionService;
import com.nuite.mobile.model.ResultModelVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 版本更新接口
 */
@Controller
@RequestMapping("/appDownFile")
public class VersionController {
    @Resource
    VersionService versionService;

    @RequestMapping(value = "updateVersion")
    @ResponseBody
    public ResultModelVersion getCurrentVersion(HttpServletRequest request,
                                                @RequestParam String version, @RequestParam String appKey) {

        return versionService.updateVersionNumberServiceInterface(request,
                version, appKey);
    }
}
