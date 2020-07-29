package com.nuite.modules.erp.controller;

import com.nuite.common.utils.Result;
import com.nuite.common.utils.ResultStatus;
import com.nuite.modules.erp.service.CommonErpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/3 10:47
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/size")
public class GoodsSizesController extends AbstractController {

    @RequestMapping("/v1.0")
    public Result getAllGoodsSizes(HttpServletRequest request) {

        CommonErpService commonErpService = getErpService(request);

        List allGoodsSizes = commonErpService.getAllGoodsSizes();
        if (allGoodsSizes == null || allGoodsSizes.isEmpty()) {
            return Result.error(ResultStatus.DATA_NOT_EXIST.value(), ResultStatus.DATA_NOT_EXIST.getReasonPhrase());
        }
        return Result.ok(allGoodsSizes);

    }

}
