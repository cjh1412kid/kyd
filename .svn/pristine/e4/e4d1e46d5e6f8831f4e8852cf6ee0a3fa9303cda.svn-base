package io.nuite.modules.order_platform_app.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.modules.order_platform_app.dao.SmallUserInfoDao;
import io.nuite.modules.order_platform_app.entity.SmallUserInfoEntity;
import io.nuite.modules.order_platform_app.service.SmallUserInfoService;
import io.nuite.modules.sr_base.dao.BaseUserDao;
import io.nuite.modules.sr_base.entity.BaseUserEntity;


@Service
public class SmallUserInfoServiceImpl extends ServiceImpl<SmallUserInfoDao, SmallUserInfoEntity> implements SmallUserInfoService {

	@Autowired
	private BaseUserDao baseUserDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SmallUserInfoEntity> page = this.selectPage(
                new Query<SmallUserInfoEntity>(params).getPage(),
                new EntityWrapper<SmallUserInfoEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public SmallUserInfoEntity updateOrInsertUser(String openId, String sessionKey, String unionId) {
		 EntityWrapper<SmallUserInfoEntity> wrapper = new EntityWrapper<SmallUserInfoEntity>();
	        wrapper.eq("Openid", openId);
	        SmallUserInfoEntity smallUserInfoEntity = this.selectOne(wrapper);
	        if (smallUserInfoEntity == null) {
	        	smallUserInfoEntity = new SmallUserInfoEntity();
	        }
	        smallUserInfoEntity.setOpenid(openId);
	        smallUserInfoEntity.setSessionkey(sessionKey);
	        smallUserInfoEntity.setUnionid(unionId);
		     smallUserInfoEntity.setIsAdmin(0);
		     smallUserInfoEntity.setIsuse(0);
	        this.insertOrUpdate(smallUserInfoEntity);

	        return smallUserInfoEntity;
	}

	@Override
	public SmallUserInfoEntity updateOrInsertUser(String openId,String sessionKey, WxMaUserInfo wxMaUserInfo) {
		 EntityWrapper<SmallUserInfoEntity> wrapper = new EntityWrapper<SmallUserInfoEntity>();
	     wrapper.eq("Openid", openId);
	     SmallUserInfoEntity smallUserInfoEntity = this.selectOne(wrapper);
	     if (smallUserInfoEntity == null) {
	        	smallUserInfoEntity = new SmallUserInfoEntity();
	      }
	     smallUserInfoEntity.setOpenid(openId);
	     smallUserInfoEntity.setSessionkey(sessionKey);
	     smallUserInfoEntity.setAvatarUrl(wxMaUserInfo.getAvatarUrl());  
	     smallUserInfoEntity.setCity(wxMaUserInfo.getCity());
	     smallUserInfoEntity.setCountry(wxMaUserInfo.getCountry());
	     smallUserInfoEntity.setIsAdmin(0);
	     smallUserInfoEntity.setIsuse(0);
	     smallUserInfoEntity.setNickname(wxMaUserInfo.getNickName());
	     smallUserInfoEntity.setOpenid(openId);
	     smallUserInfoEntity.setProvince(wxMaUserInfo.getProvince());
	     smallUserInfoEntity.setSessionkey(sessionKey);
	     smallUserInfoEntity.setSex(Integer.parseInt(wxMaUserInfo.getGender()));
	     smallUserInfoEntity.setUnionid(wxMaUserInfo.getUnionId());
	     this.insertOrUpdate(smallUserInfoEntity);
		return smallUserInfoEntity;
	}

	@Override
	public SmallUserInfoEntity updateOrInsertUser(String openId,WxMaPhoneNumberInfo wxMaPhoneNumberInfo,BaseUserEntity baseUserEntity) {
		 EntityWrapper<SmallUserInfoEntity> wrapper = new EntityWrapper<SmallUserInfoEntity>();
		  wrapper.eq("Openid", openId); 
		  SmallUserInfoEntity smallUserInfoEntity = this.selectOne(wrapper);
		  if (smallUserInfoEntity == null) {
		       smallUserInfoEntity = new SmallUserInfoEntity();
		   }
		  smallUserInfoEntity.setTelephone(wxMaPhoneNumberInfo.getPhoneNumber());
		  if(baseUserEntity!=null) {
			  smallUserInfoEntity.setIsAdmin(1);
			  smallUserInfoEntity.setUserSeq(baseUserEntity.getSeq());
		  }
		  this.insertOrUpdate(smallUserInfoEntity);
		return smallUserInfoEntity;
	}

}
