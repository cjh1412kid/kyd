package io.nuite.modules.order_platform_app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartEntity;
import io.nuite.modules.order_platform_app.service.MeetingShoppingCartService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.service.MeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订货平台2期 - 订货会购物车相关
 * @author yy
 * @date 2018-04-16 13:47
 */
@RestController
@RequestMapping("/order/app/meetingShoppingCart")
@Api(tags = "订货平台2期 - 订货会购物车相关")
public class MeetingShoppingCartController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MeetingService meetingService;
    
    @Autowired
    private MeetingShoppingCartService meetingShoppingCartService;
    
    
    
    /**
     * 选中购物车
     */
    @Login
    @GetMapping("checkShoppingCart")
    @ApiOperation("选中购物车")
    public R checkShoppingCart(@ApiParam("购物车Seq") @RequestParam("meetingShoppingCartSeq") Integer meetingShoppingCartSeq,
    		@ApiParam("是否选中") @RequestParam("isChecked") Integer isChecked) {
    	try {
			MeetingShoppingCartEntity shoppingCartEntity = new MeetingShoppingCartEntity();
			shoppingCartEntity.setSeq(meetingShoppingCartSeq);
			shoppingCartEntity.setIsChecked(isChecked);
			meetingShoppingCartService.updateById(shoppingCartEntity);
    		
			return R.ok("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }

    
    /**
     * 购物车全选/全不选
     */
    @Login
    @GetMapping("checkAllShoppingCart")
    @ApiOperation("购物车全选/全不选")
    public R checkAllShoppingCart(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("是否选中") @RequestParam("isChecked") Integer isChecked) {
    	try {
    		MeetingEntity meetingEntity = meetingService.getNowMeetingEntity(loginUser.getCompanySeq());
    		if(meetingEntity == null) {
    			return R.error("没有正在进行的订货会");
    		}
    		
    		meetingShoppingCartService.checkAllShoppingCart(meetingEntity.getSeq(), loginUser.getSeq(), isChecked);
			return R.ok("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
    /**
     * 批量删除购物车
     */
    @Login
    @GetMapping("deleteShoppingCart")
    @ApiOperation("批量删除购物车")
    public R deleteShoppingCart(@ApiParam("购物车Seq(逗号分隔)") @RequestParam("meetingShoppingCartSeqs") List<Integer> meetingShoppingCartSeqs) {
    	try {
    		meetingShoppingCartService.deleteMeetingShoppingCart(meetingShoppingCartSeqs);
			return R.ok("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
}
