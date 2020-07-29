package io.nuite.modules.order_platform_app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import io.nuite.modules.order_platform_app.service.PushRecordService;
import io.nuite.modules.order_platform_app.utils.JPushUtils;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.entity.MeetingDeviceEntity;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.entity.MeetingPermissionEntity;
import io.nuite.modules.system.service.MeetingDeviceService;
import io.nuite.modules.system.service.MeetingPermissionService;
import io.nuite.modules.system.service.MeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 订货平台2期 - 订货会权限控制相关接口
 * @author yy
 * @date 2019-08-16 13:47
 */
@RestController
@RequestMapping("/order/app/meetingPermission")
@Api(tags = "订货平台2期 - 订货会权限控制相关接口")
public class MeetingGoodsPermissionController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MeetingDeviceService meetingDeviceService;
    
    @Autowired
    private JPushUtils jPushUtils;
    
    @Autowired
    private MeetingService meetingService;
    
    @Autowired
    private MeetingPermissionService meetingPermissionService;
    
    @Autowired
    private PushRecordService pushRecordService;
    
    @Autowired
    private MeetingUserIllegalActsService meetingUserIllegalActsService;
    
    
    
    /*权限控制一： 手机所处位置限制  */
    //1.打开快易订app，判断手机蓝牙是否打开，不打开直接退出app
    
    
    //2.打开app后，直接调用后台“获取所有设备列表”接口，获取专用手机uuid、蓝牙设备uuid列表，判断本手机是专用手机还是个人手机
    
    
    //3.从此开始定时任务，定时检测手机蓝牙接收到的信号，如果是专用手机，失去蓝牙设备信号，如果是个人手机，接收到蓝牙设备信号，“发送手机推送消息接口”通知工厂管理员
    
    
    
    /*权限控制二： 订货会权限判断  */
    //1.点击"订货会"按钮（这里必须已登录），根据是个人手机还是专用手机，判断是否需要打开扫码摄像
    
    //2.专用手机扫码摄像后，调用后台“获取用户 在 指定订货会序号的订货会 的权限信息”接口，返回：1.订货会状态 2.订货会基本信息 3.用户参加本次订货会权限信息
    //2.个人手机点击后，调用后台“获取用户 在 当前进行中订货会 的权限信息”接口，返回：1.订货会状态 2.订货会基本信息 3.用户参加本次订货会权限信息
    
    
    
    
    /**
     *	获取所有设备列表
     */
    @Login
    @GetMapping("getAllMeetingDevice")
    @ApiOperation("获取所有设备列表")
    public R getAllMeetingDevice() {
    	try {
    		
    		List<MeetingDeviceEntity> bleList = meetingDeviceService.getAllMeetingDevice(0);
    		List<MeetingDeviceEntity> phoneList = meetingDeviceService.getAllMeetingDevice(1);
    		
    		Map<String, Object> resultMap = new HashMap<String, Object>();
    		resultMap.put("phone", phoneList);
    		resultMap.put("ble", bleList);
    		
    		return R.ok(resultMap);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
    
    
    /**
     * 给管理员发推送，告知用户有非法拍照行为接口
     */
    @Login
    @PostMapping("sendJpushToFactoryUser")
    @ApiOperation("给管理员发推送，告知用户有非法拍照行为接口")
    public R sendJpushToFactoryUser(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("接收消息人公司序号（即专用手机或蓝牙设备所属公司序号）") @RequestParam("companySeq") Integer companySeq,
    		@ApiParam("消息内容") @RequestParam("message") String message) {
    	try {
    		
    	    //所有工厂账号
            List<BaseUserEntity> userList = baseService.getAllCompanyUser(companySeq);
            List<String> aliasList = new ArrayList<String>();
            for (BaseUserEntity user : userList) {
            	aliasList.add(user.getAccountName());
            }
            jPushUtils.sendPush(aliasList, message, message, "-2");
    		
            //保存推送消息
            for (BaseUserEntity user : userList) {
                pushRecordService.addPushRecord(loginUser.getSeq(), user.getSeq(), user.getAccountName(), 4, null, message);
            }
            
            
            
            /**2019-09-02记录用户非法行为**/
            //查询用户最后一条行为记录
    		MeetingUserIllegalActsEntity meetingUserIllegalActsEntity = meetingUserIllegalActsService.getLastUserIllegalActsEntity(loginUser.getSeq());
    		if(meetingUserIllegalActsEntity == null) { // 没扫码入场记录的情况（一般不会有）
    			meetingUserIllegalActsEntity = new MeetingUserIllegalActsEntity();
        		Date nowDate = new Date();
        		meetingUserIllegalActsEntity.setCompanySeq(loginUser.getCompanySeq());
        		meetingUserIllegalActsEntity.setUserSeq(loginUser.getSeq());
        		meetingUserIllegalActsEntity.setLockTime(nowDate);
        		meetingUserIllegalActsEntity.setState(1);
        		meetingUserIllegalActsEntity.setInputTime(nowDate);
        		meetingUserIllegalActsEntity.setDel(0);
        		meetingUserIllegalActsService.insert(meetingUserIllegalActsEntity);
    		} else {
	    		meetingUserIllegalActsEntity.setLockTime(new Date());
	    		meetingUserIllegalActsEntity.setState(1);
	    		meetingUserIllegalActsService.updateById(meetingUserIllegalActsEntity);
    		}
            
    		return R.ok("推送成功");
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
    /**
     * 给用户发推送，解除非法拍照用户的手机锁定接口
     */
    @Login
    @PostMapping("sendJpushRelieveLocked")
    @ApiOperation("给用户发推送，解除非法拍照用户的手机锁定接口")
    public R sendJpushRelieveLocked(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("接收消息人用户序号") @RequestParam("userSeq") Integer userSeq,
    		@ApiParam("接收消息人账号") @RequestParam("accountName") String accountName,
    		@ApiParam("消息内容（可不传）") @RequestParam(value = "message", required = false) String message) {
    	try {
    		//权限判断 
    		if(!isFactoryUser(loginUser)) {
    			return R.error("非工厂用户，权限不足");
    		}
    		
    	    //账号accountName
            List<String> aliasList = new ArrayList<String>();
            aliasList.add(accountName);
            if(StringUtils.isBlank(message)) {
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	message = "管理员"+ loginUser.getUserName() + "于" + sdf.format(new Date()) + " 已为您解除锁定";
            }
            jPushUtils.sendPush(aliasList, null, message, "-3");
    		
            //保存推送消息
            pushRecordService.addPushRecord(loginUser.getSeq(), userSeq, accountName, 4, null, message);
            
            
            /**2019-09-02记录解除用户非法行为**/
    		Date nowDate = new Date();
    		Integer meetingSeq = null;
            //查询用户最后一条行为记录
    		MeetingUserIllegalActsEntity meetingUserIllegalActsEntity = meetingUserIllegalActsService.getLastUserIllegalActsEntity(userSeq);
    		if(meetingUserIllegalActsEntity == null) { // 没扫码入场记录的情况（一般不会有）
    			meetingUserIllegalActsEntity = new MeetingUserIllegalActsEntity();
        		meetingUserIllegalActsEntity.setCompanySeq(loginUser.getCompanySeq());
        		meetingUserIllegalActsEntity.setUserSeq(userSeq);
        		meetingUserIllegalActsEntity.setUnlockTime(nowDate);
        		meetingUserIllegalActsEntity.setState(2);
        		meetingUserIllegalActsEntity.setInputTime(nowDate);
        		meetingUserIllegalActsEntity.setDel(0);
        		meetingUserIllegalActsService.insert(meetingUserIllegalActsEntity);
    		} else {
    			meetingSeq = meetingUserIllegalActsEntity.getMeetingSeq();
	    		meetingUserIllegalActsEntity.setUnlockTime(nowDate);
	    		meetingUserIllegalActsEntity.setState(2);
	    		meetingUserIllegalActsService.updateById(meetingUserIllegalActsEntity);
    		}
    		
    		//新增一条扫码入场记录
    		meetingUserIllegalActsEntity = new MeetingUserIllegalActsEntity();
    		meetingUserIllegalActsEntity.setCompanySeq(loginUser.getCompanySeq());
    		meetingUserIllegalActsEntity.setUserSeq(userSeq);
    		meetingUserIllegalActsEntity.setMeetingSeq(meetingSeq);
    		meetingUserIllegalActsEntity.setScanTime(nowDate);
    		meetingUserIllegalActsEntity.setState(0);
    		meetingUserIllegalActsEntity.setInputTime(nowDate);
    		meetingUserIllegalActsEntity.setDel(0);
    		meetingUserIllegalActsService.insert(meetingUserIllegalActsEntity);
    		
    		
    		return R.ok("推送成功");
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
    
    /**
     * 获取用户 在 指定订货会序号的订货会 的权限信息
     * @return 返回：1.订货会状态 2.订货会基本信息 3.用户参加本次订货会权限信息
     */
    @Login
    @GetMapping("getUserMeetingPermissionBySeq")
    @ApiOperation("获取用户 在 指定订货会序号的订货会 的权限信息")
    public R getUserMeetingPermissionBySeq(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("订货会序号") @RequestParam("meetingSeq") Integer meetingSeq) {
    	try {
    		
    		//获取用户权限信息
    		MeetingPermissionEntity meetingPermissionEntity = meetingPermissionService.getMeetingPermission(loginUser.getSeq(), meetingSeq);
    		if(meetingPermissionEntity == null) {
    			return R.error("抱歉，您没有本次订货会的权限，无法参加本次订货会");
    		}
    		
    		//获取订货会
    		MeetingEntity meetingEntity = meetingService.selectById(meetingSeq);
    		//判断当前时间是否在订货会时间内
    		Date nowDate = new Date();
    		if(nowDate.before(meetingEntity.getStartTime()) ) {
    			return R.error("此订货会尚未开始，开始时间:" + meetingEntity.getStartTime());
    		} else if(nowDate.after(meetingEntity.getEndTime())) {
    			return R.error("抱歉，此订货会已结束");
    		}
    		
    		return R.ok(meetingEntity);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
    /**
     * 获取用户 在 当前进行中订货会 的权限信息
     * @return 返回：1.订货会状态 2.订货会基本信息 3.用户参加本次订货会权限信息
     */
    @Login
    @GetMapping("getUserMeetingPermissionNow")
    @ApiOperation("获取用户 在 当前进行中订货会 的权限信息")
    public R getUserMeetingPermissionNow(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
    	try {
    		
    		// 当前正在进行的订货会
    		MeetingEntity meetingEntity = meetingService.getNowMeetingEntity(loginUser.getCompanySeq());
    		if(meetingEntity == null) {
    			return R.error("没有正在进行的订货会");
    		}
    		
    		//获取用户权限信息
    		MeetingPermissionEntity meetingPermissionEntity = meetingPermissionService.getMeetingPermission(loginUser.getSeq(), meetingEntity.getSeq());
    		if(meetingPermissionEntity == null) {
    			return R.error("抱歉，您没有本次订货会的权限，无法参加本次订货会");
    		}
    		
    		//判断当前时间是否在订货会时间内
    		Date nowDate = new Date();
    		if(nowDate.before(meetingEntity.getStartTime()) ) {
    			return R.error("此订货会尚未开始，开始时间:" + meetingEntity.getStartTime());
    		} else if(nowDate.after(meetingEntity.getEndTime())) {
    			return R.error("抱歉，此订货会已结束");
    		}
    		
    		return R.ok(meetingEntity);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
}