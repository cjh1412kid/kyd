package io.nuite.modules.order_platform_app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.nuite.modules.order_platform_app.service.CommunityService;
import io.nuite.modules.order_platform_app.service.PushRecordService;
import io.nuite.modules.order_platform_app.utils.GotyeUtils;
import io.nuite.modules.order_platform_app.utils.JPushUtils;
import io.nuite.modules.order_platform_app.utils.RongCloudUtils;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.service.BaseBrandService;
import io.nuite.modules.sr_base.service.BaseCompanyService;
import io.nuite.modules.sr_base.service.BaseUserService;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户基本信息接口
 * 
 * @author yy
 * @date 2018-04-27 13:47
 */
@RestController
@RequestMapping("/order/app/baseUser")
@Api(tags = "订货平台 - 用户基本信息", description = "修改用户基本信息 + 直播相关")
public class BaseUserController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BaseUserService baseUserService;

	@Autowired
	private RongCloudUtils rongCloudUtils;
	
	@Autowired
	private GotyeUtils gotyeUtils;
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private JPushUtils jPushUtils;
	
    @Autowired
    private PushRecordService pushRecordService;
    
    @Autowired
    private BaseCompanyService baseCompanyService;
    
    @Autowired
    private BaseBrandService baseBrandService;
	
	
	/**
	 * 修改用户基本信息
	 */
	@Login
	@PostMapping("updateBaseUser")
	@ApiOperation("修改用户基本信息")
	public R updateBaseUser(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
			@ApiParam("姓名") @RequestParam(value = "userName", required = false) String userName,
			@ApiParam("电话") @RequestParam(value = "telephone", required = false) String telephone,
			HttpServletRequest request) {
		try {
			if(userName != null || telephone != null) {
				BaseUserEntity baseUserEntity = new BaseUserEntity();
				baseUserEntity.setSeq(userSeq);
				if(userName != null && !userName.equals("")) {
					baseUserEntity.setUserName(userName);
					//修改姓名后需要刷新融云平台用户信息
//					rongCloudUtils.refreshUserInfo(userSeq, userName, getBaseUserPictureUrl(userSeq.toString()) + baseUserEntity.getHeadImg());
					rongCloudUtils.refreshUserInfo(userSeq, userName, "1.jpg");
				}
				if(telephone != null && !telephone.equals("")) {
					baseUserEntity.setTelephone(telephone);
				}
				baseUserService.updateBaseUser(baseUserEntity);
			}
			return R.ok("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("修改失败");
		}
	}



	/**
	 * 修改用户密码
	 */
	@Login
	@PostMapping("updateUserPassword")
	@ApiOperation("修改用户密码")
	public R updateUserPassword(@ApiIgnore @LoginUser BaseUserEntity loginUser,
			@ApiParam("原密码") @RequestParam(value = "oldPassword", required = false) String oldPassword,
			@ApiParam("新密码") @RequestParam(value = "newPassword", required = false) String newPassword,
			HttpServletRequest request) {
		try {
			//判断密码是否正确
			if(!DigestUtils.sha256Hex(oldPassword).equals(loginUser.getPassword())) {
				return R.error(HttpStatus.SC_FORBIDDEN, "密码错误");
			}
			BaseUserEntity baseUserEntity = new BaseUserEntity();
			baseUserEntity.setSeq(loginUser.getSeq());
			baseUserEntity.setPassword(DigestUtils.sha256Hex(newPassword));
			baseUserService.updateBaseUser(baseUserEntity);
			return R.ok("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("修改失败");
		}
	}
	
	
	
	/**
	 * 更换用户头像
	 */
	@Login
	@PostMapping("changeHeadImg")
	@ApiOperation("更换用户头像")
	public R changeHeadImg(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
			@ApiParam("用户头像") @RequestParam("headImg") MultipartFile headImg,
			HttpServletRequest request) {
		try {
			BaseUserEntity baseUserEntity = new BaseUserEntity();
			baseUserEntity.setSeq(userSeq);
			baseUserEntity.setHeadImg(upLoadFile(userSeq, getBaseUserUploadUrl(request, userSeq.toString()), headImg));
			baseUserService.updateBaseUser(baseUserEntity);
			
			Map<String, Object> map = new HashMap<>();
			map.put("headImg", getBaseUserPictureUrl(userSeq.toString()) + baseUserEntity.getHeadImg());
			return R.ok(map).put("msg", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("修改失败");
		}
	}
	
	
	
	/**
     * 获取亲加直播，主播室Id、密码
     */
    @Login
    @GetMapping("getGotyeRoomIdPwd")
    @ApiOperation("获取亲加直播，主播室Id、密码、房间状态")
    public R getGotyeRoomIdPwd(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
    	try {
    		//1.查询登录用户所属的工厂管理员
    		BaseUserEntity adminUser = baseService.getAdminUserByLoginUser(loginUser);
    		//工厂方的用户编号，即创建直播间的creator
    		Integer seq = adminUser.getSeq();
    		
    		JSONObject json = gotyeUtils.getRooms(seq.toString());
    		Map<String, Object> map = new HashMap<String, Object>();
    		if(json != null) {
    			map.put("roomId", json.getString("roomId"));
    			if(isFactoryAdmin(loginUser)) {
    				//主播密码
    				map.put("password", json.getString("anchorPwd"));
    			} else {
    				//观众密码
    				map.put("password", json.getString("userPwd"));
    			}
    			map.put("status", json.getString("status"));
    		} else {
    			return R.error("获取失败");
    		}
			return R.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
	/**
     * 直播开始发送推送消息
     */
    @Login
    @PostMapping("gotyeLiveStartPush")
    @ApiOperation("直播开始发送推送消息")
    public R gotyeLiveStartPush(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("推送消息内容") @RequestParam("pushContent") String pushContent) {
    	try {
    		
    		if(!isFactoryAdmin(loginUser)) {
    			return R.error(HttpStatus.SC_FORBIDDEN, "对不起，您的权限不足");
    		}
    		
			// 属于工厂公司的所有有效用户seq
			List<Integer> allUserSeqList = communityService.getAllValidUser(loginUser.getCompanySeq(), loginUser.getBrandSeq());
			List<BaseUserEntity> baseUserEntityList = baseUserService.selectBatchIds(allUserSeqList);
    		
    		List<String> aliasList = new ArrayList<String>();
    		for(BaseUserEntity baseUserEntity : baseUserEntityList) {
    			aliasList.add(baseUserEntity.getAccountName());
    		}
    		jPushUtils.sendPush(aliasList, pushContent, pushContent, "-1");
    		
			//保存推送消息
			try {
	    		for(BaseUserEntity baseUserEntity : baseUserEntityList) {
	    			pushRecordService.addPushRecord(loginUser.getSeq(), baseUserEntity.getSeq(), baseUserEntity.getAccountName(), 2, null, pushContent);
	    		}
	    	} catch (Exception e) {
				e.printStackTrace();
				logger.error("保存直播开始推送消息失败" + e.getMessage(), e);
			}
			
			return R.ok("推送成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("直播开始发送推送消息" + e.getMessage(), e);
			return R.error("推送失败");
		}

    }

    

	/**
     * 关于我们（获取工厂公司、品牌信息）
     */
    @Login
    @GetMapping("getCompanyBrandInfo")
    @ApiOperation("关于我们（公司、品牌信息）")
    public R getCompanyBrandInfo(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
    	try {
    		Map<String, Object> companyMap = baseCompanyService.getCompanyMapBySeq(loginUser.getCompanySeq());
    		Map<String, Object> brandMap = baseBrandService.getBrandMapBySeq(loginUser.getBrandSeq());
    			//处理品牌图片路径
    		brandMap.put("brandImage", getBaseBrandPictureUrl(loginUser.getBrandSeq().toString()) + brandMap.get("brandImage"));
    		
    		companyMap.putAll(brandMap);
			return R.ok(companyMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("获取失败");
		}

    }
    
    
    
	/**
	 * 客服列表，获取工厂账号和工厂子账号列表
	 */
	@Login
	@GetMapping("getCompanyUsers")
	@ApiOperation("客服列表，获取工厂账号和工厂子账号列表")
	public R getCompanyUsers(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
		try {
			List<BaseUserEntity> companyUsersList = baseUserService.getCompanyUsers(loginUser.getCompanySeq());
			for(BaseUserEntity baseUserEntity : companyUsersList) {
				baseUserEntity.setHeadImg(getBaseUserPictureUrl(baseUserEntity.getSeq().toString()) + baseUserEntity.getHeadImg());
			}
			return R.ok(companyUsersList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("获取客服列表失败");
		}
	}
    
    

}
