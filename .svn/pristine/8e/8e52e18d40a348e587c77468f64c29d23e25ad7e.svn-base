package io.nuite.modules.order_platform_app.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.order_platform_app.entity.MeetingUserIllegalActsEntity;
import io.nuite.modules.order_platform_app.service.MeetingUserIllegalActsService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 订货平台2期 - 订货会，用户非法行为记录接口
 * @author yy
 * @date 2019-09-02 13:18:15
 */
@RestController
@Api(tags="订货平台2期 - 订货会，用户非法行为记录接口")
@RequestMapping("/order/app/meetingUserIllegalActs")
public class MeetingUserIllegalActsController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private MeetingUserIllegalActsService meetingUserIllegalActsService;
    
    
    
    /**
     * 获取用户当前锁定状态(0:正常 1:锁定)
     */
    @Login
    @GetMapping("getUserIllegalActsState")
    @ApiOperation("获取用户当前锁定状态(0:正常 1:锁定)")
    public R getUserIllegalActsState(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
    	try {
    		
    		//获取用户当前锁定状态(0:正常 1:锁定)
    		Integer state = meetingUserIllegalActsService.getUserIllegalActsState(loginUser.getSeq());
    		
    		return R.ok(state);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
    /**
     * 获取用户某次订货会最后一次扫码入场的时间
     */
    @Login
    @GetMapping("getUserLastScanInTime")
    @ApiOperation("获取用户某次订货会最后一次扫码入场的时间")
    public R getUserLastScanInTime(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("订货会序号") @RequestParam("meetingSeq") Integer meetingSeq) {
    	try {
    		
    		//获取用户当前锁定状态(0:正常 1:锁定)
    		Date userLastScanInTime = meetingUserIllegalActsService.getUserLastScanInTime(loginUser.getSeq(), meetingSeq);
    		
    		return R.ok(userLastScanInTime);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    

    
    
    /**
     * 扫码入场（存一条记录）
     */
    @Login
    @PostMapping("userScanIn")
    @ApiOperation("扫码入场")
    public R userScanIn(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("订货会序号") @RequestParam("meetingSeq") Integer meetingSeq) {
    	try {
    		//判断有无锁定状态
    		Integer state = meetingUserIllegalActsService.getUserIllegalActsState(loginUser.getSeq());
    		if(state == 1) {
    			return R.error("用户当前处于锁定状态，禁止扫码入场！");
    		}
    		
    		
    		Date nowDate = new Date();
    		MeetingUserIllegalActsEntity meetingUserIllegalActsEntity = new MeetingUserIllegalActsEntity();
    		meetingUserIllegalActsEntity.setCompanySeq(loginUser.getCompanySeq());
    		meetingUserIllegalActsEntity.setUserSeq(loginUser.getSeq());
    		meetingUserIllegalActsEntity.setMeetingSeq(meetingSeq);
    		meetingUserIllegalActsEntity.setScanTime(nowDate);
    		meetingUserIllegalActsEntity.setState(0);
    		meetingUserIllegalActsEntity.setInputTime(nowDate);
    		meetingUserIllegalActsEntity.setDel(0);
    		meetingUserIllegalActsService.insert(meetingUserIllegalActsEntity);
    		
    		return R.ok();
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    

}
