package io.nuite.modules.online_sales_app.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import io.jsonwebtoken.Claims;
import io.nuite.common.utils.JwtUtils;
import io.nuite.common.utils.R;
import io.nuite.config.HttpRequestUtil;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.MiniAppEntity;
import io.nuite.modules.online_sales_app.entity.OrderEntity;
import io.nuite.modules.online_sales_app.service.CustomerUserInfoService;
import io.nuite.modules.online_sales_app.service.OlsMiniAppService;
import io.nuite.modules.online_sales_app.service.OnlineOrderService;
import io.nuite.modules.online_sales_app.wx.WxMyService;
import io.nuite.modules.online_sales_app.wx.WxServiceUtils;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.service.BaseUserService;
import io.nuite.weixin.AesCbcUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/online/miniapp")
@Api(tags = "线上销售APP登录接口", description = "登录")
public class OnlineLoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerUserInfoService customerUserInfoService;

    @Autowired
    private OlsMiniAppService olsMiniAppService;

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private BaseUserService baseUserService;
    
    @Autowired
    private OnlineOrderService onlineOrderService;

    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation("登录")
    public R login(String code, String miniapp) {
        if (StringUtils.isBlank(code)) {
            return R.error("empty jscode");
        }

        if (StringUtils.isBlank(miniapp)) {
            return R.error("miniapp error");
        }

        MiniAppEntity miniAppEntity = olsMiniAppService.selectById(miniapp);
        if (miniAppEntity == null) {
            return R.error("miniapp error");
        }

        try {
            WxMyService wxMyService = WxServiceUtils.getWxMyService(miniAppEntity);
            if (wxMyService == null) {
                return R.error(-1, "company in empty");
            }
            WxMaJscode2SessionResult session = wxMyService.getWxMaService().getUserService().getSessionInfo(code);
            String openId = session.getOpenid();
            String sessionKey = session.getSessionKey();
            String unionId = session.getUnionid();

            CustomerUserInfo customerUserInfo = customerUserInfoService.updateOrInsertUser(openId, sessionKey, unionId, miniAppEntity);
            Integer isAdmin=0;
            if(customerUserInfo.getTelephone()!=null) {
            	String telephone=customerUserInfo.getTelephone();
            	Integer companySeq=customerUserInfo.getCompanySeq();
            	Integer  brandSeq=customerUserInfo.getBrandSeq();
            	BaseUserEntity baseUserEntity=baseUserService.getUserByPhoneAndCompany(telephone, companySeq,brandSeq);
            	if(baseUserEntity!=null) {
            		isAdmin=1;
            	}
            		
            }
            Integer userSeq=customerUserInfo.getSeq();
            //根据userSeq查询订单数据
            List<OrderEntity> orderEntities=onlineOrderService.getOrderByUserSeq(userSeq);
            Integer isOrder=0;
            if(orderEntities.size()>0) {
            	isOrder=1;
            }
            // 生成token
            String token = jwtUtils.generateToken(customerUserInfo.getSeq());
            return R.ok("").put("token", token).put("customerUserInfo", customerUserInfo).put("isAdmin", isAdmin).put("isOrder", isOrder);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return R.error(e.toString());
        }
    }
    
    
    /**
     * xcx登录
     */
    @PostMapping("loginXcx")
    @ApiOperation("登录")
    public R loginXcx(String encryptedData, String iv, String code, String miniapp) {
        if (StringUtils.isBlank(code)) {
            return R.error("empty jscode");
        }

        if (StringUtils.isBlank(miniapp)) {
            return R.error("miniapp error");
        }

        MiniAppEntity miniAppEntity = olsMiniAppService.selectById(miniapp);
        if (miniAppEntity == null) {
            return R.error("miniapp error");
        }
        
        //授权（必填）
        String grant_type = "authorization_code";

        ////////////////1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + miniAppEntity.getAppId() + "&secret=" + miniAppEntity.getAppSecret() + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequestUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        if(StringUtils.isEmpty(sr)){
        	return R.error("miniapp error");
        }
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.parseObject(sr);
        //获取会话密钥（session_key）
        String session_key = "";
        try {
          session_key = json.get("session_key") + "";
        } catch (Exception e) {
          logger.info(json.get("session_key").toString());
        }
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
              JSONObject userInfoJSON = JSONObject.parseObject(result);
            
              String openId=userInfoJSON.getString("openId");
              String unionId=userInfoJSON.getString("unionId");
              String nickName=userInfoJSON.getString("nickName");
              String sex=userInfoJSON.getString("gender");
              String province=userInfoJSON.getString("province");
              String country=userInfoJSON.getString("country");
              String city= userInfoJSON.getString("city");
              
              CustomerUserInfo customerUserInfo = customerUserInfoService.updateOrInsertCustomer(openId, session_key, unionId, miniAppEntity, nickName, sex, province, country, city);

              return R.ok("").put("customerUserInfo", customerUserInfo);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        return R.error("解密失败");
    }
    
    /**
     * xcx登录
     */
    @PostMapping("getPhone")
    @ApiOperation("登录")
    public R getPhone(String encryptedData, String iv,String code,String session_key,String miniapp,Integer userSeq) {

        if (StringUtils.isBlank(miniapp)) {
            return R.error("miniapp error");
        }

        MiniAppEntity miniAppEntity = olsMiniAppService.selectById(miniapp);
        if (miniAppEntity == null) {
            return R.error("miniapp error");
        }
        if(StringUtils.isEmpty(session_key)){
        //授权（必填）
        String grant_type = "authorization_code";

        ////////////////1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + miniAppEntity.getAppId() + "&secret=" + miniAppEntity.getAppSecret() + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequestUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        if(StringUtils.isEmpty(sr)){
        	return R.error("miniapp error");
        }
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.parseObject(sr);
        //获取会话密钥（session_key）
        try {
          session_key = json.get("session_key") + "";
        } catch (Exception e) {
          logger.info(json.get("session_key").toString());
        }
        }
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
              JSONObject userInfoJSON = JSONObject.parseObject(result);
              String phone=userInfoJSON.getString("phoneNumber");
              CustomerUserInfo customerUserInfo=customerUserInfoService.selectById(userSeq);
              customerUserInfo.setTelephone(phone);
              customerUserInfoService.insertOrUpdate(customerUserInfo);
              String telephone=customerUserInfo.getTelephone();
              Integer companySeq=customerUserInfo.getCompanySeq();
              Integer  brandSeq=customerUserInfo.getBrandSeq();
          		BaseUserEntity baseUserEntity=baseUserService.getUserByPhoneAndCompany(telephone, companySeq,brandSeq);
          		Integer isAdmin=0;
          		if(baseUserEntity!=null) {
          		isAdmin=1;
          		}
              
              return R.ok("").put("customerUserInfo", customerUserInfo).put("isAdmin", isAdmin);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        return R.error("解密失败");
    }
    
    
    
    
    
    
    
    

    /**
     * 校验token
     */
    @PostMapping("check")
    @ApiOperation("token校验")
    public R check(String token) {
        if (StringUtils.isBlank(token)) {
            return R.error("empty token");
        }

        Claims claims = jwtUtils.getClaimByToken(token);
        if (claims == null) {
            return R.ok("toekn error").put("errorCode", -1);
        }
        Long userSeq = Long.parseLong(claims.getSubject());
        CustomerUserInfo customerUserInfo = customerUserInfoService.selectById(userSeq);
        if (customerUserInfo == null) {
            return R.ok("no the user").put("errorCode", -2);
        }
        if (jwtUtils.isTokenExpired(claims.getExpiration())) {
            String newToken = jwtUtils.generateToken(Long.parseLong(claims.getSubject()));
            return R.ok(newToken).put("errorCode", -3);
        }
        return R.ok("").put("errorCode", 0);
    }
}
