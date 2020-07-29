package com.nuite.modules.erp.controller;

import com.nuite.common.utils.Result;
import com.nuite.modules.erp.bserp.entity.Dingdan;
import com.nuite.modules.erp.service.CommonErpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 订单通过接口
 */
@RestController
@RequestMapping("dingdan")
public class DingdanController extends AbstractController {

    @RequestMapping("v1.0")
    public Result dingdanV1(HttpServletRequest request, Date startDate) {
        CommonErpService commonErpService = getErpService(request);
        List<Dingdan> allDingdan = commonErpService.getAllDingdan(startDate);

        if (allDingdan == null || allDingdan.isEmpty()) {
            return Result.error(-1,"未查询到数据");
        }
        return Result.ok().put("result", allDingdan);
    }
}
