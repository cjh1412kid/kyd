package io.nuite.modules.order_platform_app.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import io.nuite.modules.order_platform_app.entity.MeetingGoodsValuateEntity;
import io.nuite.modules.order_platform_app.service.MeetingGoodsValuateService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.service.BaseUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 订货平台2期 - 订货会商品建议接口
 * @author yy
 * @date 2019-08-16 13:47
 */
@RestController
@RequestMapping("/order/app/meetingGoodsValuate")
@Api(tags = "订货平台2期 - 订货会商品建议接口")
public class MeetingGoodsValuateController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MeetingGoodsValuateService meetingGoodsValuateService;
    
    @Autowired
    private BaseUserService baseUserService;
    
    
    /**
     * 货品建议列表
     */
    @Login
    @GetMapping("meetingGoodsValuateList")
    @ApiOperation("货品建议列表")
    public R meetingGoodsValuateList(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("订货鞋子序号") @RequestParam("meetingGoodsSeq") Integer meetingGoodsSeq,
			@ApiParam("起始条数") @RequestParam("start") Integer start,
    		@ApiParam("总条数") @RequestParam("num") Integer num) {
    	try {
    		
    		List<Map<String, Object>> list = meetingGoodsValuateService.getMeetingGoodsValuateList(meetingGoodsSeq, start, num);
    		
    		//添加用户名、用户头像
    		for(Map<String, Object> map : list) {
    			Integer userSeq = (Integer)map.get("userSeq");
    			BaseUserEntity baseUserEntity = baseUserService.selectById(userSeq);
    			map.put("userName", baseUserEntity.getUserName());
    			//头像处理
    			map.put("userHeadImg", getBaseUserPictureUrl(baseUserEntity.getSeq().toString()) + baseUserEntity.getHeadImg());
    		}
    		
    		return R.ok(list);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
    
    
    /**
     * 新增或修改建议
     */
    @Login
    @PostMapping("addMeetingGoodsValuate")
    @ApiOperation("新增或修改建议")
    public R addMeetingGoodsValuate(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("订货鞋子序号") @RequestParam("meetingGoodsSeq") Integer meetingGoodsSeq,
    		@ApiParam(value="建议序号(有则是修改)", required=false) @RequestParam(value="suggestSeq",required = false) Integer suggestSeq,
			@ApiParam("建议文字") @RequestParam("suggest") String suggest) {
    	try {
    		
//    		Seq					int	0	0	0	-1	0	0		0	0	0	0			-1			
//    		User_Seq			int	0	0	0	0	0	0		0	0	0	0			0			
//    		MeetingGoods_Seq	int	0	0	0	0	0	0		0	0	0	0			0			
//    		Suggest				varchar	250	0	0	0	0	0		0	0	0	0		Chinese_PRC_CI_AS	0			
//    		InputTime			datetime	0	0	0	0	0	0	(getdate())	0	0	0	0			0			
//    		Del					int	0	0	0	0	0	0	((0))	0	0	0	0			0			

    		
    		MeetingGoodsValuateEntity meetingGoodsValuateEntity = new MeetingGoodsValuateEntity();
    		meetingGoodsValuateEntity.setSeq(suggestSeq);
    		meetingGoodsValuateEntity.setUserSeq(loginUser.getSeq());
    		meetingGoodsValuateEntity.setMeetingGoodsSeq(meetingGoodsSeq);
    		meetingGoodsValuateEntity.setSuggest(suggest);
    		meetingGoodsValuateEntity.setInputTime(new Date());
    		meetingGoodsValuateEntity.setDel(0);
    		meetingGoodsValuateService.insertOrUpdate(meetingGoodsValuateEntity);
    		
    		return R.ok("提交成功");
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
    
    
    /**
     * 删除建议
     */
    @Login
    @PostMapping("deleteMeetingGoodsValuate")
    @ApiOperation("删除建议")
    public R deleteMeetingGoodsValuate(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("建议序号") @RequestParam("suggestSeq") Integer suggestSeq) {
    	try {
    		
    		meetingGoodsValuateService.deleteById(suggestSeq);
    		
    		return R.ok("删除成功");
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
    
}