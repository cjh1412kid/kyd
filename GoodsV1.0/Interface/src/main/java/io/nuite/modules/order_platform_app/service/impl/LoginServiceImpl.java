package io.nuite.modules.order_platform_app.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.nuite.common.exception.RRException;
import io.nuite.common.validator.Assert;
import io.nuite.modules.order_platform_app.dao.UserJurisdictionDao;
import io.nuite.modules.order_platform_app.entity.UserJurisdictionEntity;
import io.nuite.modules.order_platform_app.form.OnlineLoginForm;
import io.nuite.modules.order_platform_app.service.LoginService;
import io.nuite.modules.sr_base.dao.BaseUserDao;
import io.nuite.modules.sr_base.entity.BaseUserEntity;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private BaseUserDao baseUserDao;
    
    @Autowired
    private UserJurisdictionDao userJurisdictionDao;
    
    
    /**
     * 登录验证
     */
    @Override
    public BaseUserEntity login(OnlineLoginForm form) {
    	String accountName  = form.getAccountName();
    	//1.根据账号查询用户
        BaseUserEntity baseUser = getBaseUserByAccountName(accountName);
	    if(baseUser == null) {
	        //2.如果没有查询到用户，再根据手机号查询
	    	baseUser = getBaseUserByTelephone(accountName);
	    	if(baseUser == null) {
	    		//3.如果还是没有查询到用户，再根据用户名（姓名）查询
		    	baseUser = getBaseUserByUserName(accountName);
	    	}
	    }
        
        //没有找到用户
        if (baseUser == null) {
            throw new RRException("账号或密码错误");
        }

        //密码错误
        if (!baseUser.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))) {
            throw new RRException("账号或密码错误");
        }
        
        //权限信息验证
        UserJurisdictionEntity userJurisdiction = getUserJurisdictionByUserSeq(baseUser.getSeq());
        Assert.isNull(userJurisdiction, "账号或密码错误");
        
        if(userJurisdiction.getIsDisable() == 1) {
        	throw new RRException("对不起，账号已停用");
        }
        if(userJurisdiction.getEffectiveDate().before(new Date())) {
        	throw new RRException("对不起，账号已过期");
        }
        
        baseUser.setPassword(null);
        return baseUser;
    }
	
	
	
    //根据accountName查询用户
    private BaseUserEntity getBaseUserByAccountName(String accountName) {
        BaseUserEntity baseUserEntity = new BaseUserEntity();
        baseUserEntity.setAccountName(accountName);
        return baseUserDao.selectOne(baseUserEntity);
    }
    
    //根据telephone查询用户
    private BaseUserEntity getBaseUserByTelephone(String telephone) {
        Wrapper<BaseUserEntity> wrapper = new EntityWrapper<BaseUserEntity>();
		wrapper.where("Telephone = {0}", telephone);
		List<BaseUserEntity> userList = baseUserDao.selectList(wrapper);
		if(userList == null || userList.size() == 0) {
			return null;
		} else if(userList.size() > 1) {
        	throw new RRException("此手机号关联多个账号，请使用账号登录");
        } else {
        	return userList.get(0);
        }
    }
    
    //根据userName查询用户
    private BaseUserEntity getBaseUserByUserName(String userName) {
        Wrapper<BaseUserEntity> wrapper = new EntityWrapper<BaseUserEntity>();
		wrapper.where("UserName = {0}", userName);
		List<BaseUserEntity> userList = baseUserDao.selectList(wrapper);
		if(userList == null || userList.size() == 0) {
			return null;
		} else if(userList.size() > 1) {
        	throw new RRException("此姓名关联多个账号，请使用账号登录");
        } else {
        	return userList.get(0);
        }
    }
    
    
    //根据用户seq查询用户权限
    private UserJurisdictionEntity getUserJurisdictionByUserSeq(Integer seq) {
    	UserJurisdictionEntity userJurisdictionEntity = new UserJurisdictionEntity();
    	userJurisdictionEntity.setUserSeq(seq);
        return userJurisdictionDao.selectOne(userJurisdictionEntity);
    }
    
    
}
