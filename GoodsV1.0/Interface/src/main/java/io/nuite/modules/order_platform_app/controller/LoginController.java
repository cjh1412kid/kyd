package io.nuite.modules.order_platform_app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.nuite.common.utils.JwtUtils;
import io.nuite.common.utils.R;
import io.nuite.common.validator.ValidatorUtils;
import io.nuite.modules.order_platform_app.dao.MeetingRemindDao;
import io.nuite.modules.order_platform_app.entity.MeetingRemindEntity;
import io.nuite.modules.order_platform_app.form.OnlineLoginForm;
import io.nuite.modules.order_platform_app.service.LoginService;
import io.nuite.modules.sr_base.dao.BaseCompanyDao;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.service.ShiroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 登录接口
 * @author chenshun
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/order/app")
@Api(tags = "订货平台 - 登录", description = "登录")
public class LoginController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private JwtUtils jwtUtils;

    @Autowired
    private BaseCompanyDao baseCompanyDao;
    
    @Autowired
    private ShiroService shiroService;
    
    @Autowired
    private MeetingRemindDao meetingRemindDao;
	
	/**
	 * 登录
	 */
	@PostMapping("onLogin")
	@ApiOperation("登录")
	public R login(String accountName, String password) {
		try {
			
			// 表单验证
			OnlineLoginForm onlineLoginForm = new OnlineLoginForm();
			onlineLoginForm.setAccountName(accountName);
			onlineLoginForm.setPassword(password);
			ValidatorUtils.validateEntity(onlineLoginForm);

			// 登录校验，并返回用户信息
			BaseUserEntity baseUser = loginService.login(onlineLoginForm);
				//头像处理
			baseUser.setHeadImg(getBaseUserPictureUrl(baseUser.getSeq().toString()) + baseUser.getHeadImg());
				//添加公司名称
			String companyName = baseCompanyDao.selectById(baseUser.getCompanySeq()).getName();
			baseUser.setCompanyName(companyName);
				//添加工厂管理员seq
			Integer adminUserSeq = baseService.getAdminUserByLoginUser(baseUser).getSeq();
			baseUser.setAdminUserSeq(adminUserSeq);
				//添加权限
			Set<String> permissions = shiroService.getUserPermissions(baseUser.getSeq());
			baseUser.setPermissions(permissions);
			
				//判断用户所属工厂是否开通了订货计划权限
			Set<String> factoryPermissions = shiroService.getUserPermissions(baseService.getAdminUserByLoginUser(baseUser).getSeq());
			if(factoryPermissions.contains("meetingPlan:list")) {  //开通了订货计划权限
				baseUser.setMeetingPlanPermission(1);
				
				//添加订货会提醒
				Wrapper<MeetingRemindEntity> wrapper = new EntityWrapper<MeetingRemindEntity>();
				wrapper.where("User_Seq = {0}", baseUser.getSeq());
				List<MeetingRemindEntity> meetingRemindList = meetingRemindDao.selectList(wrapper);
				if(meetingRemindList.size() == 0) {
					baseUser.setScanRemind(0);
					baseUser.setOrderRemind(0);
				} else {
					baseUser.setScanRemind(meetingRemindList.get(0).getScanRemind());
					baseUser.setOrderRemind(meetingRemindList.get(0).getOrderRemind());
				}
			} else {
				baseUser.setMeetingPlanPermission(0);
			}
			
			// 生成token
			String token = jwtUtils.generateToken(baseUser.getSeq());
			
			Map<String, Object> map = new HashMap<>();
			map.put("token", token);
			map.put("expire", jwtUtils.getExpire());
			map.put("userInfo", baseUser);

			return R.ok(map).put("msg", "登录成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error(HttpStatus.SC_UNAUTHORIZED, e.getMessage());
		}
	}

	
}
