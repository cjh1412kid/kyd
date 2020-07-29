package com.nuite.modules.erp.controller;

import com.nuite.common.utils.Result;
import com.nuite.common.utils.ResultStatus;
import com.nuite.modules.erp.bserp.entity.Brand;
import com.nuite.modules.erp.service.CommonErpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController extends AbstractController {

    @RequestMapping("/v1.0")
    public Result getAllBrands(HttpServletRequest request) {

        CommonErpService commonErpService = getErpService(request);

        List<Brand> allBrands = commonErpService.getAllBrands();
        if (allBrands == null || allBrands.isEmpty()) {
            return Result.error(ResultStatus.DATA_NOT_EXIST.value(), ResultStatus.DATA_NOT_EXIST.getReasonPhrase());
        }
        return Result.ok(allBrands);

    }
}
