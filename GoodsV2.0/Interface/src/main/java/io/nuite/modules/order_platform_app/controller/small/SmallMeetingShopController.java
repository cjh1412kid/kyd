package io.nuite.modules.order_platform_app.controller.small;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.annotation.XcxLogin;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.service.MeetingGoodsService;
import io.nuite.modules.order_platform_app.service.MeetingOrderService;
import io.nuite.modules.order_platform_app.service.MeetingShoppingCartService;
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
@RequestMapping("/order/app/meetingShop")
@Api(tags = "销售分析小程序——订货会中数据")
public class SmallMeetingShopController {

	@Autowired
	private MeetingShoppingCartService meetingShoppingCartService;
	
	@Autowired
	private MeetingOrderService meetingOrderService;
	
	@Autowired
	private MeetingGoodsService meetingGoodsService;
	
	@Autowired
	private BaseUserService baseUserService;
	
	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	
    @XcxLogin
	@PostMapping("rankList")
	@ApiOperation("筛板排行榜")
	public R getRankList(@ApiParam("排行榜类型(1.单品，2.区域，3.订货方，4.品类,5.筛板与总量的占比 )") @RequestParam("type") Integer type,@ApiParam("订货会Seq") @RequestParam("meetingSeq") Integer meetingSeq,@ApiParam("排行类型") @RequestParam("showType") Integer showType) {
		if(type==1) {//单品排行
			List<Map<String, Object>> maps=meetingShoppingCartService.getGoodsRank(meetingSeq,showType);
			for (Map<String, Object> map : maps) {
				Integer meetingGoodsSeq=(Integer) map.get("meetingGoodsSeq");
				MeetingGoodsEntity meetingGoodsEntity=meetingGoodsService.selectById(meetingGoodsSeq);
				map.put("meetingGoodsId", meetingGoodsEntity.getGoodID());
			}
			Integer count=meetingGoodsService.getMeetingGoodsNum(meetingSeq);//订货会总货品数量
			Integer chooseNum=meetingShoppingCartService.getMeetingGoodsNum(meetingSeq);//选款数量
			Integer  orderNum=meetingShoppingCartService.getMeetingGoodsCount(meetingSeq);//订货数量
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("count",count );
			map.put("chooseNum",chooseNum );
			map.put("orderNum",orderNum );
			map.put("maps", maps);
			return R.ok(map);
		}else if(type==3){//区域排行
			List<Object> meetingGoodsSeqs = meetingGoodsService.getAllMeetingGoodsSeqsByMeetingSeq(meetingSeq);
			if(meetingGoodsSeqs.size()>0) {
				List<Map<String, Object>> maps=meetingShoppingCartService.getAreaRank(meetingGoodsSeqs,showType);
				return R.ok(maps);
			}else {
				List<Map<String, Object>> maps=new ArrayList<Map<String,Object>>();
				return R.ok(maps);
			}
		}else if(type==4){//订货方排行
			List<Map<String, Object>> maps=meetingShoppingCartService.getUserRank(meetingSeq,showType);
			for (Map<String, Object> map : maps) {
				Integer userSeq=(Integer) map.get("userSeq");
				BaseUserEntity baseUserEntity=baseUserService.selectById(userSeq);
				map.put("userName", baseUserEntity.getUserName());
			}
			return R.ok(maps);
		}else if(type==2){//品类排行
			Integer  orderNum=meetingShoppingCartService.getMeetingGoodsCount(meetingSeq);//订货数量
			//获取该公司的一级分类
			MeetingEntity meetingEntity=meetingService.selectById(meetingSeq);
			List<Map<String, Object>> goodsCategoryList=meetingGoodsService.getParentCategory(meetingEntity.getCompanySeq(), meetingSeq, 0,showType);
			
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
			List<Map<String, Object>> manCategoryList=new ArrayList<Map<String,Object>>();
			if(manCategorySeq!=null) {
				//获取三级分类
				manCategoryList=meetingGoodsService.getParentCategory(meetingEntity.getCompanySeq(), meetingSeq, manCategorySeq,showType);
				for (Map<String, Object> map : manCategoryList) {
					Integer cateSeq=(Integer) map.get("categorySeq");
					GoodsCategoryEntity categoryEntity=goodsCategoryService.selectById(cateSeq);
					GoodsCategoryEntity parentCategory=goodsCategoryService.selectById(categoryEntity.getParentSeq());
					map.put("categoryName",parentCategory.getName()+"/"+categoryEntity.getName());
				}
				

				
			}
			//女鞋分类占比
			List<Map<String, Object>> womanCategoryList=new ArrayList<Map<String,Object>>();
			if(womanCategorySeq!=null) {
				List<GoodsCategoryEntity> goodsCategoryEntities2=goodsCategoryService.getGoodsCategoryByParent(womanCategorySeq,meetingEntity.getCompanySeq());
				//获取三级分类
			
				womanCategoryList=meetingGoodsService.getParentCategory(meetingEntity.getCompanySeq(), meetingSeq,womanCategorySeq,showType);
				for (Map<String, Object> map : womanCategoryList) {
					Integer cateSeq=(Integer) map.get("categorySeq");
					GoodsCategoryEntity categoryEntity=goodsCategoryService.selectById(cateSeq);
					GoodsCategoryEntity parentCategory=goodsCategoryService.selectById(categoryEntity.getParentSeq());
					map.put("categoryName", parentCategory.getName()+"/"+categoryEntity.getName());
				}
			}
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("goodsCategoryList",goodsCategoryList );
			map.put("manCategoryList",manCategoryList );
			map.put("womanCategoryList",womanCategoryList );
			map.put("orderNum", orderNum);
			return R.ok(map);
		}
		return R.ok();
	}
}
