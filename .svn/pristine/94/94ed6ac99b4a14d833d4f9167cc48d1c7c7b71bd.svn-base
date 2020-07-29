package com.nuite.modules.erp.controller;

import com.alibaba.fastjson.JSON;
import com.nuite.common.utils.Result;
import com.nuite.common.utils.ResultStatus;
import com.nuite.modules.erp.entity.vo.GoodsDataVo;
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
@RequestMapping("/stock")
public class StockController extends AbstractController {

    @RequestMapping("/v1.0")
    public Result getAllGoodsSizes(HttpServletRequest request,String data) {

        CommonErpService commonErpService = getErpService(request);

        List<String> goodIds = JSON.parseArray(data, String.class);

        List<GoodsDataVo> result = commonErpService.getStockInfo(goodIds);

        if (result == null || result.isEmpty()) {
            //数据不存在
            return Result.error(ResultStatus.DATA_NOT_EXIST.value(), ResultStatus.DATA_NOT_EXIST.getReasonPhrase());
        }
        return Result.ok(result);
    }

}
