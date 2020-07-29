package io.nuite.modules.order_platform_app.service;


import io.nuite.modules.order_platform_app.form.OnlineLoginForm;
import io.nuite.modules.sr_base.entity.BaseUserEntity;

/**
 * 用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:06
 */
public interface LoginService {

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	BaseUserEntity login(OnlineLoginForm form);

}
