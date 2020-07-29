package io.nuite.modules.order_platform_app.controller.small;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.annotation.XcxLogin;
import io.nuite.modules.order_platform_app.controller.BaseController;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.service.MeetingGoodsService;
import io.nuite.modules.order_platform_app.service.MeetingOrderService;
import io.nuite.modules.order_platform_app.service.MeetingRankService;
import io.nuite.modules.order_platform_app.service.MeetingShoppingCartService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.service.BaseUserService;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.system.entity.MeetingAreaEntity;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.service.MeetingAreaService;
import io.nuite.modules.system.service.MeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/order/app/meetingRank")
@Api(tags = "销售分析小程序——订货会后数据")
public class SmallMeeingRankController extends BaseController {

	@Autowired
	private MeetingShoppingCartService meetingShoppingCartService;

	@Autowired
	private MeetingOrderService meetingOrderService;

	@Autowired
	private MeetingGoodsService meetingGoodsService;

	@Autowired
	private BaseUserService baseUserService;

	@Autowired
	private MeetingRankService meetingRankService;
	
	@Autowired
	private MeetingAreaService meetingAreaService;
	
	@Autowired
	private MeetingService meetingService;

	@Autowired
	private GoodsCategoryService goodsCategoryService;

	@XcxLogin
	@PostMapping("meetingData")
	public R meetingData(@ApiParam("订货会Seq") @RequestParam("meetingSeq") Integer meetingSeq) {
		// 头部相关数据
		Map<String, Object> totalMap = meetingRankService.totalData(meetingSeq);

		// 单品前五
		List<Map<String, Object>> goodsMaps = meetingRankService.getGoodsIdRank(meetingSeq, 5);
		for (Map<String, Object> map : goodsMaps) {
			Integer meetingGoodsSeq = (Integer) map.get("meetingGoodsSeq");
			MeetingGoodsEntity meetingGoodsEntity = meetingGoodsService.selectById(meetingGoodsSeq);
			map.put("meetingGoodsId", meetingGoodsEntity.getGoodID());
			map.put("img", getMeetingGoodsPictureUrl(meetingGoodsEntity.getGoodID()) + meetingGoodsEntity.getImg());
		}
		// 订货方前五
		List<Map<String, Object>> userMaps = meetingRankService.getUserRank(meetingSeq, 5);
		for (Map<String, Object> map : userMaps) {
			Integer userSeq = (Integer) map.get("userSeq");
			BaseUserEntity baseUserEntity = baseUserService.selectById(userSeq);
			map.put("userName", baseUserEntity.getUserName());
			// 查询订货款数
			Integer totalOrderKindByUser = meetingRankService.totalOrderKindByUser(meetingSeq, userSeq);
			map.put("totalOrderKind", totalOrderKindByUser);
		}
		// 区域前五
		List<Object> meetingGoodsSeqs = meetingGoodsService.getAllMeetingGoodsSeqsByMeetingSeq(meetingSeq);
		List<Map<String, Object>> areaMaps = new ArrayList<Map<String, Object>>();
		if (meetingGoodsSeqs.size() > 0) {
			areaMaps = meetingRankService.getAreaRank(meetingGoodsSeqs, 5);
		}
		for (Map<String, Object> map : areaMaps) {
			Integer areaSeq = (Integer) map.get("areaSeq");
			// 查询订货款式
			Integer totalOrderKindByArea = meetingRankService.totalOrderKindByArea(meetingSeq, areaSeq);
			map.put("totalOrderKind", totalOrderKindByArea);
			List<Map<String, Object>> areaMap=meetingRankService.getUserRankByAreaSeq(areaSeq, meetingSeq);
			Integer count=0;
			for (Map<String, Object> map2 : areaMap) {
				count+=(Integer)map2.get("count");
			}
			map.put("count", count);
		}

		return R.ok().put("totalMap", totalMap).put("goodsMaps", goodsMaps).put("userMaps", userMaps).put("areaMaps",
				areaMaps);
	}

	@XcxLogin
	@PostMapping("meetingDataDetail")
	public R meetingDataDetail(@ApiParam("订货会Seq") @RequestParam("meetingSeq") Integer meetingSeq,
			@ApiParam("查询类型(2.品类，3.单品，4.区域，5.订货方)") @RequestParam("type") Integer type) {
		if (type == 2) {// 品类
			MeetingEntity meetingEntity=meetingService.selectById(meetingSeq);
			List<GoodsCategoryEntity> goodsCategoryEntities=goodsCategoryService.getGoodsCategoryByParent(0,meetingEntity.getCompanySeq());
			Integer manCategorySeq=null;
			Integer womanCategorySeq=null;
			Integer childCategorySeq=null;
			for (GoodsCategoryEntity goodsCategoryEntity : goodsCategoryEntities) {
				String name=goodsCategoryEntity.getName();
				Integer seq=goodsCategoryEntity.getSeq();
				if("男鞋".equals(name)) {
					manCategorySeq=seq;
				}else if("女鞋".equals(name)) {
					womanCategorySeq=seq;
				}else if("童鞋".equals(name)) {
					childCategorySeq=seq;
				}
			}
			Integer manCateTotal=0;
			List<Map<String, Object>> manRank=new ArrayList<Map<String,Object>>();
				if(manCategorySeq!=null) {
					//男鞋总计
					 manCateTotal=meetingRankService.totalOrderByCategorySeq(manCategorySeq, meetingSeq);
					//男鞋排行
					 manRank=meetingRankService.getCategoryRank(meetingSeq, manCategorySeq);
				}
				Integer womanCateTotal=0;
				List<Map<String, Object>> womanRank=new ArrayList<Map<String,Object>>();
				if(womanRank!=null) {
					//女鞋总计
					 womanCateTotal=meetingRankService.totalOrderByCategorySeq(womanCategorySeq, meetingSeq);
					
					//女鞋排行
					 womanRank=meetingRankService.getCategoryRank(meetingSeq, womanCategorySeq);
				}
				return R.ok().put("manCateTotal", manCateTotal).put("womanCateTotal", womanCateTotal).put("manRank", manRank).put("womanRank", womanRank);
		} else if (type == 3) {// 单品
			List<Map<String, Object>> goodsMaps = meetingRankService.getGoodsIdRank(meetingSeq, 30);
			for (Map<String, Object> map : goodsMaps) {
				Integer meetingGoodsSeq = (Integer) map.get("meetingGoodsSeq");
				MeetingGoodsEntity meetingGoodsEntity = meetingGoodsService.selectById(meetingGoodsSeq);
				map.put("meetingGoodsId", meetingGoodsEntity.getGoodID());
				map.put("img", getMeetingGoodsPictureUrl(meetingGoodsEntity.getGoodID()) + meetingGoodsEntity.getImg());
			}
			return R.ok().put("goodsMaps", goodsMaps);
		} else if (type == 4) {// 区域
			List<Object> meetingGoodsSeqs = meetingGoodsService.getAllMeetingGoodsSeqsByMeetingSeq(meetingSeq);
			List<Map<String, Object>> areaMaps = new ArrayList<Map<String, Object>>();
			if (meetingGoodsSeqs.size() > 0) {
				areaMaps = meetingRankService.getAreaRank(meetingGoodsSeqs, 30);
			}
			for (Map<String, Object> map : areaMaps) {
				Integer areaSeq = (Integer) map.get("areaSeq");
				// 查询订货款式
				Integer totalOrderKindByArea = meetingRankService.totalOrderKindByArea(meetingSeq, areaSeq);
				map.put("totalOrderKind", totalOrderKindByArea);
				List<Map<String, Object>> areaMap=meetingRankService.getUserRankByAreaSeq(areaSeq, meetingSeq);
				Integer count=0;
				for (Map<String, Object> map2 : areaMap) {
					count+=(Integer)map2.get("count");
				}
				map.put("count", count);
			}
			return R.ok().put("areaMaps", areaMaps);
		} else if (type == 5) {// 订货方
			List<Map<String, Object>> userMaps = meetingRankService.getUserRank(meetingSeq, 30);
			for (Map<String, Object> map : userMaps) {
				Integer userSeq = (Integer) map.get("userSeq");
				BaseUserEntity baseUserEntity = baseUserService.selectById(userSeq);
				map.put("userName", baseUserEntity.getUserName());
				// 查询订货款数
				Integer totalOrderKindByUser = meetingRankService.totalOrderKindByUser(meetingSeq, userSeq);
				map.put("totalOrderKind", totalOrderKindByUser);
			}
			return R.ok().put("userMaps", userMaps);
		}
		return R.ok();
	}

	@XcxLogin
	@PostMapping("meetingGoodsDetail")
	public R meetingGoodsDetail(@ApiParam("货品Seq") @RequestParam("meetingGoodsSeq") Integer meetingGoodsSeq,@ApiParam("查询类型(1.订货方，2.区域)") @RequestParam("type") Integer type) {
		//货品详情
		MeetingGoodsEntity meetingGoodsEntity=meetingGoodsService.selectById(meetingGoodsSeq);
		meetingGoodsEntity.setImg(getMeetingGoodsPictureUrl(meetingGoodsEntity.getGoodID()) + meetingGoodsEntity.getImg());
		//总订单量
		Integer totalOrder=meetingRankService.totalOrderByGoodsSeq(meetingGoodsSeq);
		if(type==1) {//订货方
			List<Map<String, Object>> userMaps=meetingRankService.getUserRankByGoodsSeq(meetingGoodsSeq);
			return R.ok().put("userMaps", userMaps).put("meetingGoodsEntity", meetingGoodsEntity).put("totalOrder", totalOrder);
		}else {//区域
			List<Map<String, Object>> areaMaps=meetingRankService.getAreaRankByGoodsSeq(meetingGoodsSeq);
			return R.ok().put("areaMaps", areaMaps).put("meetingGoodsEntity", meetingGoodsEntity).put("totalOrder", totalOrder);
		}
	}
	
	
	@XcxLogin
	@PostMapping("meetingAreaDetatil")
	public R meetingAreaDetatil(@ApiParam("货品Seq") @RequestParam("meetingAreaSeq") Integer meetingAreaSeq,@ApiParam("订货会Seq") @RequestParam("meetingSeq") Integer meetingSeq) {
		//区域详情
		MeetingAreaEntity meetingAreaEntity=meetingAreaService.selectById(meetingAreaSeq);
		//总订单量
		Integer totalOrder=meetingRankService.totalOrderByAreaSeq(meetingAreaSeq, meetingSeq);
		List<Map<String, Object>> areaMaps=meetingRankService.getUserRankByAreaSeq(meetingAreaSeq, meetingSeq);
		for (Map<String, Object> map : areaMaps) {
			Integer userSeq = (Integer) map.get("userSeq");
			BaseUserEntity baseUserEntity = baseUserService.selectById(userSeq);
			map.put("userName", baseUserEntity.getUserName());
			// 查询订货款数
			Integer totalOrderKindByUser = meetingRankService.totalOrderKindByUser(meetingSeq, userSeq);
			map.put("totalOrderKind", totalOrderKindByUser);
		}
		MeetingEntity meetingEntity=meetingService.selectById(meetingSeq);
		List<GoodsCategoryEntity> goodsCategoryEntities=goodsCategoryService.getGoodsCategoryByParent(0,meetingEntity.getCompanySeq());
		Integer manCategorySeq=null;
		Integer womanCategorySeq=null;
		Integer childCategorySeq=null;
		for (GoodsCategoryEntity goodsCategoryEntity : goodsCategoryEntities) {
			String name=goodsCategoryEntity.getName();
			Integer seq=goodsCategoryEntity.getSeq();
			if("男鞋".equals(name)) {
				manCategorySeq=seq;
			}else if("女鞋".equals(name)) {
				womanCategorySeq=seq;
			}else if("童鞋".equals(name)) {
				childCategorySeq=seq;
			}
		}
		//区域内品类分析
		List<Map<String, Object>>  categoryRank=meetingRankService.getCategoryRankByArea(meetingSeq, manCategorySeq,meetingAreaSeq);
		categoryRank.addAll(meetingRankService.getCategoryRankByArea(meetingSeq, womanCategorySeq,meetingAreaSeq));
		categoryRank.addAll(meetingRankService.getCategoryRankByArea(meetingSeq, childCategorySeq,meetingAreaSeq));
		
		return R.ok().put("meetingAreaEntity", meetingAreaEntity).put("totalOrder", totalOrder).put("areaMaps", areaMaps).put("categoryRank", categoryRank);
	}
	
	@XcxLogin
	@PostMapping("meetingUserDetatil")
	public R meetingUserDetatil(@ApiParam("货品Seq") @RequestParam("meetingUserSeq") Integer meetingUserSeq,@ApiParam("订货会Seq") @RequestParam("meetingSeq") Integer meetingSeq) {
		//订货方详情
		BaseUserEntity baseUserEntity=baseUserService.selectById(meetingUserSeq);
		//总订单量
		Integer totalOrder=meetingRankService.totalOrderByUserSeq(meetingUserSeq,meetingSeq);
		//总货款
		Integer totalOrderKind=meetingRankService.totalOrderKindByUserSeq(meetingUserSeq,meetingSeq);
		
		//订单列表
		List<Map<String, Object>> orderMap=meetingRankService.getGoodRankByUserSeq(meetingUserSeq, meetingSeq);
		
		MeetingEntity meetingEntity=meetingService.selectById(meetingSeq);
		List<GoodsCategoryEntity> goodsCategoryEntities=goodsCategoryService.getGoodsCategoryByParent(0,meetingEntity.getCompanySeq());
		Integer manCategorySeq=null;
		Integer womanCategorySeq=null;
		Integer childCategorySeq=null;
		for (GoodsCategoryEntity goodsCategoryEntity : goodsCategoryEntities) {
			String name=goodsCategoryEntity.getName();
			Integer seq=goodsCategoryEntity.getSeq();
			if("男鞋".equals(name)) {
				manCategorySeq=seq;
			}else if("女鞋".equals(name)) {
				womanCategorySeq=seq;
			}else if("童鞋".equals(name)) {
				childCategorySeq=seq;
			}
		}
		//区域内品类分析
		List<Map<String, Object>>  categoryRank=meetingRankService.getCategoryRankByUser(meetingSeq, manCategorySeq,meetingUserSeq);
		categoryRank.addAll(meetingRankService.getCategoryRankByUser(meetingSeq, womanCategorySeq,meetingUserSeq));
		categoryRank.addAll(meetingRankService.getCategoryRankByUser(meetingSeq, childCategorySeq,meetingUserSeq));
		
		return R.ok().put("baseUserEntity", baseUserEntity).put("totalOrder", totalOrder).put("totalOrderKind", totalOrderKind).put("orderMap", orderMap).put("categoryRank", categoryRank);
	}
	
	@XcxLogin
	@PostMapping("meetingCategoryDetail")
	public R meetingCategoryDetail(@ApiParam("分类seq") @RequestParam("categorySeq") Integer categorySeq,@ApiParam("订货会seq") @RequestParam("meetingSeq") Integer meetingSeq,@ApiParam("查询类型(1.订货方，2.区域)") @RequestParam("type") Integer type) {
		Integer  cateTotal=meetingRankService.totalOrderByCategorySeq(categorySeq, meetingSeq);
		GoodsCategoryEntity goodsCategoryEntity=goodsCategoryService.selectById(categorySeq);
		GoodsCategoryEntity parentCategory=goodsCategoryService.selectById(goodsCategoryEntity.getParentSeq());
		if(type==1) {
			List<Map<String,Object>> userMaps=meetingRankService.getUserRankByCategorySeq(meetingSeq, categorySeq);
			return R.ok().put("cateTotal", cateTotal).put("goodsCategoryEntity", goodsCategoryEntity).put("parentCategory", parentCategory).put("userMaps", userMaps);
		}else {
			List<Map<String,Object>> areaMaps=meetingRankService.getAreaRankByCategorySeq(meetingSeq, categorySeq);
			return R.ok().put("cateTotal", cateTotal).put("goodsCategoryEntity", goodsCategoryEntity).put("parentCategory", parentCategory).put("areaMaps", areaMaps);
		}
		
	}
}
