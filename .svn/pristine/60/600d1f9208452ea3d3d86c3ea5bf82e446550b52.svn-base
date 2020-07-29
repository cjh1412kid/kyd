package com.nuite.modules.erp.controller;

import com.nuite.common.utils.Result;
import com.nuite.modules.erp.service.CommonErpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单状态变更，修改库存
 */
@Slf4j
@RestController
@RequestMapping("dingdan.status")
public class DingdanStatusController extends AbstractController {
    @RequestMapping("v1.0")
    public Result dingdanStatusV1(HttpServletRequest request, String data) {
        try {
            CommonErpService commonErpService = getErpService(request);

            commonErpService.changeOrderStock(data);
            return Result.ok();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return Result.error("订单库存修改失败");
        }
    }
}
