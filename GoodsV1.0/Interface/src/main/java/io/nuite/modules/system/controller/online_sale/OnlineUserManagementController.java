package io.nuite.modules.system.controller.online_sale;

import io.nuite.modules.system.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.system.service.online_sale.OnlineUserManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 在线销售平台的用户管理
 *
 * @author fl
 * @date 2018-05-12
 */
@RestController
@RequestMapping("/online/user")
@Api(tags = "后台  - 在线销售平台", description = "用户管理")
public class OnlineUserManagementController extends AbstractController {
    @Autowired
    private OnlineUserManagementService onlineUserManagementService;

    @PostMapping("/list")
    @ApiOperation("在线销售用户列表")
    public R userList(@ApiParam("关键字查询") @RequestParam("keywords") String keywords,
            @ApiParam("当前页码") @RequestParam("page") Integer page,
            @ApiParam("每页记录数") @RequestParam("limit") Integer limit) {
        try {
            PageUtils companyBrandPage = onlineUserManagementService.getUsersList(getUserSeq(), keywords, page, limit);
            return R.ok().put("page", companyBrandPage);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("在线销售用户列表获取失败");
        }
    }

}
