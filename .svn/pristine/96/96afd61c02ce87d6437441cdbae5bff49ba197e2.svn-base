package io.nuite.modules.order_platform_app.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.nuite.common.utils.PageUtils;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.MiniAppEntity;
import io.nuite.modules.order_platform_app.entity.SmallUserInfoEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;


public interface SmallUserInfoService extends IService<SmallUserInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    SmallUserInfoEntity updateOrInsertUser(String openId, String sessionKey, String unionId);
    
    SmallUserInfoEntity updateOrInsertUser(String openId,String sessionKey, WxMaUserInfo wxMaUserInfo);
    
    SmallUserInfoEntity updateOrInsertUser(String openId,WxMaPhoneNumberInfo wxMaPhoneNumberInfo,BaseUserEntity baseUserEntity);
}

