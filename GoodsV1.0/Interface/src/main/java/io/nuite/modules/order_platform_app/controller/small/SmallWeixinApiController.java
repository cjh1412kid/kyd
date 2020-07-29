package io.nuite.modules.order_platform_app.controller.small;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.jsonwebtoken.Claims;
import io.nuite.common.utils.JwtUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.service.OlsMiniAppService;
import io.nuite.modules.online_sales_app.wx.WxMyService;
import io.nuite.modules.online_sales_app.wx.WxServiceUtils;
import io.nuite.modules.order_platform_app.controller.BaseController;
import io.nuite.modules.order_platform_app.entity.SmallUserInfoEntity;
import io.nuite.modules.order_platform_app.service.SmallUserInfoService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.service.BaseUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.exception.WxErrorException;

@RestController
@RequestMapping("/order/app")
@Api(tags = "销售分析小程序——微信授权")
public class SmallWeixinApiController extends BaseController {

	@Value("${wxspAppid}")private String wxspAppid;
    
	@Value("${wxspSecret}")private String wxspSecret;
	
	@Autowired
	private BaseUserService baseUserService;
	

    @Autowired
    private JwtUtils jwtUtils;
    

    @Autowired
    private SmallUserInfoService smallUserInfoService;
	
	 protected static final Log logger = LogFactory.getLog(SmallWeixinApiController.class);
	
	  /**
	     * 登录
	     */
	    @PostMapping("login")
	    @ApiOperation("登录")
	    public R login(String code) {
	        if (StringUtils.isBlank(code)) {
	            return R.error("empty jscode");
	        }
	     

	        try {
	            WxMyService wxMyService = WxServiceUtils.getWxMyService(wxspAppid,wxspSecret);
	            if (wxMyService == null) {
	                return R.error(-1, "company in empty");
	            }
	            WxMaJscode2SessionResult session = wxMyService.getWxMaService().getUserService().getSessionInfo(code);
	            String openId = session.getOpenid();
	            String sessionKey = session.getSessionKey();
	            String unionId = session.getUnionid();

	            SmallUserInfoEntity smallUserInfo = smallUserInfoService.updateOrInsertUser(openId, sessionKey, unionId);
	            if(smallUserInfo.getTelephone()!=null) {
	            	 BaseUserEntity baseUserEntity=baseUserService.getUserByPhone(smallUserInfo.getTelephone());
	            	 if(baseUserEntity!=null) {
	            		 smallUserInfo.setIsAdmin(1);
	            		 smallUserInfo.setUserSeq(baseUserEntity.getSeq());
	            		 smallUserInfoService.insertOrUpdate(smallUserInfo);
	            	 }
	            }
	            // 生成token
	            String token = jwtUtils.generateToken(smallUserInfo.getSeq());
	            return R.ok("").put("token", token).put("smallUserInfo", smallUserInfo);
	        } catch (WxErrorException e) {
	            this.logger.error(e.getMessage(), e);
	            return R.error(e.toString());
	        }
	    }
	 
	 
  @PostMapping("/decodeUserInfo")
  @ApiOperation("授权信息")
  public R decodeUserInfo(String encryptedData, String iv, String code, HttpServletResponse response
      ) throws Exception {
    //登录凭证不能为空
    if (code == null || code.length() == 0) {
      return  R.error();
    }
    try {
        WxMyService wxMyService = WxServiceUtils.getWxMyService(wxspAppid,wxspSecret);
        if (wxMyService == null) {
            return R.error(-1, "company in empty");
        }
        WxMaJscode2SessionResult session = wxMyService.getWxMaService().getUserService().getSessionInfo(code);
        String sessionKey = session.getSessionKey();
        String openId = session.getOpenid();
        
        WxMaUserInfo wxMaUserInfo= wxMyService.getWxMaService().getUserService().getUserInfo(sessionKey, encryptedData, iv);
        SmallUserInfoEntity smallUserInfo = smallUserInfoService.updateOrInsertUser(openId,sessionKey,wxMaUserInfo);
        return R.ok("").put("smallUserInfo", smallUserInfo);
    }catch (Exception e) {
    	 this.logger.error(e.getMessage(), e);
         return R.error(e.toString());
	}
  }
  
  

  @PostMapping(value="getPhone")
  @ApiOperation("授权手机号")
  public R getPhone(String encryptedData, String iv,String code,String session_key, HttpServletResponse response,
	      HttpServletRequest request,Integer userSeq) throws Exception{
	    if(StringUtils.isEmpty(session_key)&&(code == null || code.length() == 0)){
	        return  R.error("session_key或code 不能为空"); 
	    }
	    try {
	    WxMyService wxMyService = WxServiceUtils.getWxMyService(wxspAppid,wxspSecret);
	    if (wxMyService == null) {
            return R.error(-1, "company in empty");
        }
	    SmallUserInfoEntity userInfo=smallUserInfoService.selectById(userSeq);
	    String openId=userInfo.getOpenid();
	    if(StringUtils.isEmpty(session_key)){
	         WxMaJscode2SessionResult session = wxMyService.getWxMaService().getUserService().getSessionInfo(code);
	         session_key = session.getSessionKey();
	    }
	    WxMaPhoneNumberInfo wxMaPhoneNumberInfo=wxMyService.getWxMaService().getUserService().getPhoneNoInfo(session_key, encryptedData, iv);
	    BaseUserEntity baseUserEntity=baseUserService.getUserByPhone(wxMaPhoneNumberInfo.getPhoneNumber());
	    
	    SmallUserInfoEntity smallUserInfo = smallUserInfoService.updateOrInsertUser(openId,wxMaPhoneNumberInfo,baseUserEntity);
	    return R.ok("").put("smallUserInfo", smallUserInfo);
	    }catch (Exception e) {
	    	 this.logger.error(e.getMessage(), e);
	         return R.error(e.toString());
		}
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
      SmallUserInfoEntity smallUserInfoEntity = smallUserInfoService.selectById(userSeq);
      if (smallUserInfoEntity == null) {
          return R.ok("no the user").put("errorCode", -2);
      }
      if (jwtUtils.isTokenExpired(claims.getExpiration())) {
          String newToken = jwtUtils.generateToken(Long.parseLong(claims.getSubject()));
          return R.ok(newToken).put("errorCode", -3);
      }
      return R.ok("").put("errorCode", 0);
  }
}
