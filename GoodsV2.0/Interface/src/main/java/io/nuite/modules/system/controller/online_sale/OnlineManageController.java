package io.nuite.modules.system.controller.online_sale;

import io.nuite.modules.system.controller.AbstractController;
import io.nuite.modules.system.service.online_sale.OnlineManageService;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分销后台订单管理
 *
 * @author fl
 * @date 2018-04-28
 */
@RestController
@RequestMapping("/online")
@Api(tags = "后台 - 分销订单管理相关接口", description = "订单的状态跟进和删除")
public class OnlineManageController extends AbstractController {

    @Autowired
    private OnlineManageService onlineManageService;

    /**
     * 订单的查询操作
     */
    @PostMapping("/orderlist")
    @ApiOperation("货品的订单的列表")
    public R orderlist(@ApiParam("类别序号") @RequestParam("orderStatus") Integer orderStatus,
            @ApiParam("关键字查询") @RequestParam("keywords") String keywords,
            @ApiParam("当前页码") @RequestParam("page") Integer page,
            @ApiParam("每页记录数") @RequestParam("limit") Integer limit) {
        try {
            PageUtils companyBrandPage = onlineManageService.orderlist(getUserSeq(), orderStatus, keywords, page, limit);
            return R.ok().put("page", companyBrandPage);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 根据订单的seq返回该订单中所有的鞋子信息
     */
    @PostMapping("/goodsList")
    @ApiOperation("订单中鞋子的列表")
    public R goodsList(@ApiParam("订单序号") @RequestParam("seq") Integer seq) {
        try {
            PageUtils companyBrandPage = onlineManageService.goodsList(seq);
            return R.ok().put("page", companyBrandPage);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 根据订单序号删除订单
     */
    @GetMapping("/deleteOeder")
    @ApiOperation("根据订单序号删除订单 ")
    public R deleteOeder(@ApiParam("订单序号") @RequestParam("seq") Integer seq) {
        try {
            Integer i = onlineManageService.deleteOeder(seq);
            return R.ok(i);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("删除失败");
        }
    }

    /**
     * 根据订单的Seq和订单状态跟进订单状态
     */
    @PostMapping("/updateOederStatus")
    @ApiOperation("根据订单的Seq和订单状态跟进订单状态")
    public R updateOederStatus(@ApiParam("类别序号") @RequestParam("orderStatus") Integer orderStatus,
            @ApiParam("订单序号") @RequestParam("seq") Integer seq) {
        if (seq == null) {
            return R.error("订单编号不能为空！");
        }
        if (orderStatus == null) {
            return R.error("更新状态不能为空！");
        }
        try {
            // 返回操作后的类型(用户前端跳转的页面)
            return onlineManageService.updateOederStatus(orderStatus, seq);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("失败");
        }
    }

    /**
     * 根据订单的Seq查询订单状态
     */
    @GetMapping("/getOrder")
    @ApiOperation("根据订单的Seq查询订单状态")
    public R getOrder(@ApiParam("订单序号") @RequestParam("seq") Integer seq) {
        try {
            return R.ok().put("order", onlineManageService.getOrderBySeq(seq));
        } catch (Exception e) {
            return R.error("查询失败");
        }
    }
    
}
