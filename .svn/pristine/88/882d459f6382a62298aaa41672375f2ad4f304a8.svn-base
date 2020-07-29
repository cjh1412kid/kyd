package com.nuite.modules.erp.controller;

import com.nuite.common.utils.Result;
import com.nuite.modules.erp.service.CommonErpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/20 14:29
 * @Version: 1.0
 * @Description:
 */

@RestController
@RequestMapping("goods")
public class GoodsController extends AbstractController {

    @RequestMapping("/v1.0")
    public Result getAllGoods(HttpServletRequest request){
        CommonErpService commonErpService=getErpService(request);
        List allGoods = commonErpService.getAllGoods();
        if (allGoods == null || allGoods.isEmpty()) {

            return Result.error(-1,"未查询到数据");
        }
        return Result.ok(allGoods);
    }
}
