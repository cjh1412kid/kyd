package io.nuite.modules.order_platform_app.controller.small;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.annotation.XcxLogin;
import io.nuite.modules.order_platform_app.controller.BaseController;
import io.nuite.modules.order_platform_app.service.MeetingGoodsService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.service.BaseUserService;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.service.MeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/order/app/meeting")
@Api(tags = "销售分析小程序——订货会前数据")
public class SmallMeetingController extends BaseController {

	@Autowired 
	private MeetingService meetingService;
	
	@Autowired
	private BaseUserService baseUserService;
	
	@Autowired
	private MeetingGoodsService meetingGoodsService;
	

	@Autowired
	private GoodsCategoryService goodsCategoryService;

	
    @XcxLogin
	@PostMapping("meetingList")
	@ApiOperation("订货会列表")
	public R getMeetingList(@ApiParam("管理员Seq") @RequestParam("userSeq") Integer userSeq) {
		BaseUserEntity baseUserEntity=baseUserService.getBaseUserBySeq(userSeq);
		Integer companySeq=baseUserEntity.getCompanySeq();
		List<MeetingEntity> meetingEntities=meetingService.getMeetingListByCompanySeq(companySeq);
		return R.ok(meetingEntities);
	}
	
    @XcxLogin
	@PostMapping("meetingDetail")
	@ApiOperation("订货会名称及时间")
	public R getMeetingDetail(@ApiParam("订货会Seq") @RequestParam("meetingSeq") Integer meetingSeq) {
		MeetingEntity meetingEntity=meetingService.selectById(meetingSeq);
		return R.ok(meetingEntity);
	}
	
    @XcxLogin
	@PostMapping("meetingGoodsSum")
	@ApiOperation("订货会总货品数量")
	public R meetingGoodsSum(@ApiParam("订货会Seq") @RequestParam("meetingSeq") Integer meetingSeq) {
		Integer count=meetingGoodsService.getMeetingGoodsNum(meetingSeq);
		return R.ok(count);
	}
	
    @XcxLogin
	@PostMapping("getNearMeeting")
	@ApiOperation("最近的一次订货会")
	public R getNearMeeting(@ApiParam("管理员Seq") @RequestParam("userSeq") Integer userSeq) {
		BaseUserEntity baseUserEntity=baseUserService.getBaseUserBySeq(userSeq);
		Integer companySeq=baseUserEntity.getCompanySeq();
		MeetingEntity meetingEntity=meetingService.getNearMeetingEntity(companySeq);
		return R.ok(meetingEntity);
	}
	
    @XcxLogin
 	@PostMapping("getMeetingGoodsKind")
 	@ApiOperation("订货前相关数据")
 	public R getMeetingGoodsKind(@ApiParam("订货会Seq") @RequestParam("meetingSeq") Integer meetingSeq) {
    	//总数
    	Integer count=meetingGoodsService.getMeetingGoodsNum(meetingSeq);
    	//二级分类占比
    	MeetingEntity meetingEntity=meetingService.selectById(meetingSeq);
    	
    	List<Map<String, Object>> categoryList=meetingGoodsService.getCategoryList(meetingEntity.getCompanySeq(), meetingSeq, 0);
    	
    	//男女鞋分类占比
    	List<GoodsCategoryEntity> goodsCategoryEntities=goodsCategoryService.getGoodsCategoryByParent(0,meetingEntity.getCompanySeq());
		Integer manCategorySeq=null;
		Integer womanCategorySeq=null;
		for (GoodsCategoryEntity goodsCategoryEntity : goodsCategoryEntities) {
			String name=goodsCategoryEntity.getName();
			Integer seq=goodsCategoryEntity.getSeq();
			if("男鞋".equals(name)) {
				manCategorySeq=seq;
			}else if("女鞋".equals(name)) {
				womanCategorySeq=seq;
			}
		}
    	
		List<Map<String, Object>> manCategoryList=meetingGoodsService.getCategoryList(meetingEntity.getCompanySeq(), meetingSeq, manCategorySeq);
		
		List<Map<String, Object>> womanCategoryList=meetingGoodsService.getCategoryList(meetingEntity.getCompanySeq(), meetingSeq, womanCategorySeq);
		
 		return R.ok().put("count", count).put("categoryList", categoryList).put("manCategoryList", manCategoryList).put("womanCategoryList", womanCategoryList);
 	}
    
}
