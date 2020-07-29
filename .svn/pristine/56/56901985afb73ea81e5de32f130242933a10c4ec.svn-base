package io.nuite.modules.order_platform_app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.base.Joiner;

import io.nuite.modules.order_platform_app.dao.MeetingGoodsDao;
import io.nuite.modules.order_platform_app.dao.MeetingOrderCartDao;
import io.nuite.modules.order_platform_app.dao.MeetingRankDao;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDao;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartEntity;
import io.nuite.modules.order_platform_app.service.MeetingRankService;
import io.nuite.modules.sr_base.dao.BaseUserDao;
import io.nuite.modules.sr_base.dao.GoodsCategoryDao;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.dao.MeetingAreaDao;
import io.nuite.modules.system.dao.MeetingOrderDao;
import io.nuite.modules.system.entity.MeetingAreaEntity;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.service.MeetingService;


@Service
public class MeetingRankServiceImpl implements MeetingRankService {

	@Autowired
	private MeetingGoodsDao meetingGoodsDao;
	
	@Autowired
	private MeetingRankDao meetingRankDao;
	
	@Autowired
	private MeetingShoppingCartDao meetingShoppingCartDao;
	
	@Autowired
	private BaseUserDao baseUserDao;
	
	@Autowired
	private MeetingAreaDao meetingAreaDao;
	
	@Autowired
	private MeetingOrderDao meetingOrderDao;
	
	@Autowired
	private MeetingOrderCartDao meetingOrderCartDao;
	
	@Autowired
	private ConfigUtils configUtils;
	
	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private GoodsCategoryService goodsCategoryService;

	@Autowired
	private GoodsCategoryDao goodsCategoryDao;
	
	private String orderPlatformDir() {
		return configUtils.getPictureBaseUrl() + "/" + configUtils.getOrderPlatformApp().getOrderPlatformDir() + "/";
	}
    
    /**
     * 查询区域订货量的排行
     */
	@Override
	public List<Map<String, Object>> getMeetingGoodsAreaRank(List<Object> meetingGoodsSeqList) {
		String meetingGoodsSeqs = Joiner.on(",").join(meetingGoodsSeqList);
		return meetingGoodsDao.getMeetingGoodsAreaRank(meetingGoodsSeqs);
	}



	/**
	 * 查询鞋子货号订货量的排行
	 */
	@Override
	public List<Map<String, Object>> getMeetingGoodsIdRank(Integer meetingSeq, Integer areaSeq) {
		if(areaSeq == null || areaSeq == 0) {
			//查询货号订货量的排行
			return meetingGoodsDao.getMeetingGoodsIdRank(meetingSeq);
		} else {
			//查询某一区域内 货号订货量的排行
			return meetingGoodsDao.getAreaMeetingGoodsIdRank(meetingSeq, areaSeq);
		}
	}


	
	@Override
	public List<Map<String, Object>> getMeetingGoodsNumRank(Integer meetingSeq) {
		return meetingGoodsDao.getMeetingGoodsNumRank(meetingSeq);
	}



	@Override
	public Map<String, Object> totalData(Integer meetingSeq) {
		Map<String, Object> totalMap=new HashMap<String, Object>();
		//总订货量
		 Integer totalOrderProduct=meetingShoppingCartDao.totalOrderProduct(meetingSeq);
		 //总订单量
		 Integer totalOrder=meetingShoppingCartDao.totalOrder(meetingSeq);
		 //总货款
		 Integer totalOrderKind=meetingShoppingCartDao.totalOrderKind(meetingSeq);
		 //总订货方
		Integer totalOrderUser=meetingShoppingCartDao.totalOrderUser(meetingSeq);
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
		//男鞋总订货量
		Integer manOrderProduct=meetingShoppingCartDao.totalOrderByCategorySeq(meetingSeq, manCategorySeq);
		
		
		//女鞋总订货量
		Integer womanOrderProduct=meetingShoppingCartDao.totalOrderByCategorySeq(meetingSeq, womanCategorySeq);
		
		
		//童鞋总订货量
		Integer childOrderProduct=meetingShoppingCartDao.totalOrderByCategorySeq(meetingSeq, childCategorySeq);
		
		
		totalMap.put("totalOrderProduct", totalOrderProduct);
		totalMap.put("totalOrder", totalOrder);
		totalMap.put("totalOrderKind", totalOrderKind);
		totalMap.put("totalOrderUser", totalOrderUser);
		totalMap.put("manOrderProduct", manOrderProduct);
		totalMap.put("womanOrderProduct", womanOrderProduct);
		totalMap.put("childOrderProduct", childOrderProduct);
		return totalMap;
	}


	/**
	 * 区域排行
	 */
	@Override
	public List<Map<String, Object>> getAreaRank(List<Object> meetingGoodsSeqList,Integer totalNum) {
		String meetingGoodsSeqs = Joiner.on(",").join(meetingGoodsSeqList);
		return meetingGoodsDao.getGoodsAreaRank(meetingGoodsSeqs,totalNum);
	}


	/**
	 * 单品排行
	 */
	@Override
	public List<Map<String, Object>> getGoodsIdRank(Integer meetingSeq,Integer totalNum) {
		return meetingGoodsDao.getGoodsIdRank(meetingSeq,totalNum);
	}


	/**
	 * 订货方排行
	 */
	@Override
	public List<Map<String, Object>> getUserRank(Integer meetingSeq,Integer totalNum) {
		return meetingGoodsDao.getUserRank(meetingSeq,totalNum);
	}


	/**
	 * 用户选款数
	 */
	@Override
	public Integer totalOrderKindByUser(Integer meetingSeq, Integer userSeq) {
		return meetingShoppingCartDao.totalOrderKindByUser(meetingSeq, userSeq);
	}


	/**
	 * 区域选款数
	 */
	@Override
	public Integer totalOrderKindByArea(Integer meetingSeq, Integer areaSeq) {
		return meetingShoppingCartDao.totalOrderKindByArea(meetingSeq, areaSeq);
	}


	/**
	 * 单品总订单量
	 */
	@Override
	public Integer totalOrderByGoodsSeq(Integer meetingGoodsSeq) {
		
		return meetingShoppingCartDao.totalOrderByGoodsSeq(meetingGoodsSeq);
	}


	/**
	 * 单品详情的订货方排行
	 */
	@Override
	public List<Map<String, Object>> getUserRankByGoodsSeq(Integer meetingGoodsSeq) {
		List<Map<String, Object>> userMaps=meetingShoppingCartDao.getUserRankByGoodsSeq(meetingGoodsSeq);
		MeetingGoodsEntity meetingGoodsEntity=meetingGoodsDao.selectById(meetingGoodsSeq);
		Integer maxSize=meetingGoodsEntity.getMaxSize();
		Integer minSize=meetingGoodsEntity.getMinSize();
		for (Map<String, Object> map : userMaps) {
			Map<String, Object> sizeMap=new HashMap<String, Object>();
			Integer userSeq=(Integer) map.get("userSeq");
			BaseUserEntity baseUserEntity=baseUserDao.selectById(userSeq);
			map.put("baseUserEntity", baseUserEntity);
			for (Integer i = minSize; i <=maxSize; i++) {
				sizeMap.put(i.toString(), 0);
			}
			//当前订货方单品的订货详情数量
			List<Map<String, Object>> userMap=meetingShoppingCartDao.getUserDetailByGoodsSeq(meetingGoodsSeq, userSeq);
			for (Map<String, Object> map2 : userMap) {
				if(map2!=null) {
				Integer size=(Integer) map2.get("size");
				Integer sizeNum=(Integer) map2.get("sizeNum");
					sizeMap.put(size.toString(), sizeNum);
				}
			}
			map.put("sizeMap", sizeMap);
		}
		
		return userMaps;
	}



	@Override
	public List<Map<String, Object>> getAreaRankByGoodsSeq(Integer meetingGoodsSeq) {
		MeetingGoodsEntity meetingGoodsEntity=meetingGoodsDao.selectById(meetingGoodsSeq);
		Integer maxSize=meetingGoodsEntity.getMaxSize();
		Integer minSize=meetingGoodsEntity.getMinSize();
		List<Map<String, Object>> areaMaps=meetingShoppingCartDao.getAreaRankByGoodsSeq(meetingGoodsSeq);
		for (Map<String, Object> map : areaMaps) {
			Map<String, Object> sizeMap=new HashMap<String, Object>();
			for (Integer i = minSize; i <=maxSize; i++) {
				sizeMap.put(i.toString(), 0);
			}
			Integer areaSeq=(Integer) map.get("areaSeq");
			MeetingAreaEntity meetingAreaEntity=meetingAreaDao.selectById(areaSeq);
			map.put("meetingAreaEntity", meetingAreaEntity);
			List<Map<String, Object>> areaMap=meetingShoppingCartDao.getAreaDetailByGoodsSeq(meetingGoodsSeq, areaSeq);
			for (Map<String, Object> map2 : areaMap) {
				if(map2!=null) {
					Integer size=(Integer) map2.get("size");
					Integer sizeNum=(Integer) map2.get("sizeNum");
					sizeMap.put(size.toString(), sizeNum);
				}
			map.put("sizeMap", sizeMap);
			}
			
		}
		return areaMaps;
	}



	@Override
	public Integer totalOrderByUserSeq(Integer userSeq,Integer meetingSeq) {
		return meetingShoppingCartDao.totalOrderByUserSeq(userSeq,meetingSeq);
	}



	@Override
	public Integer totalOrderKindByUserSeq(Integer userSeq,Integer meetingSeq) {
		return meetingShoppingCartDao.totalOrderKindByUserSeq(userSeq,meetingSeq);
	}



	@Override
	public List<Map<String, Object>> getGoodRankByUserSeq(Integer userSeq,Integer meetingSeq) {
		//订单列表
		List<MeetingOrderEntity> meetingOrders=meetingOrderDao.selectOrderByMeetingSeq(meetingSeq, userSeq);
		List<Map<String, Object>> orderMap=new ArrayList<Map<String,Object>>();
		for (MeetingOrderEntity meetingOrderEntity : meetingOrders) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("meetingOrderEntity", meetingOrderEntity);
			Integer orderSeq=meetingOrderEntity.getSeq();
			Wrapper<MeetingOrderCartEntity> wrapper=new EntityWrapper<MeetingOrderCartEntity>();
			wrapper.setSqlSelect("MeetingGoods_Seq as meetingGoodsSeq,sum(TotalSelectNum) as sum");
			wrapper.where("MeetingOrderSeq = {0}", orderSeq).groupBy("MeetingGoods_Seq").orderBy("sum(TotalSelectNum)",false);
			List<Map<String, Object>> carts=meetingOrderCartDao.selectMaps(wrapper);
			for (Map<String, Object> map2 : carts) {
				Integer meetingGoodsSeq=(Integer) map2.get("meetingGoodsSeq");
				MeetingGoodsEntity meetingGoodsEntity=meetingGoodsDao.selectById(meetingGoodsSeq);
				map2.put("img",getMeetingGoodsPictureUrl(meetingGoodsEntity.getGoodID()) + meetingGoodsEntity.getImg());
				map2.put("goodId", meetingGoodsEntity.getGoodID());
			}
			map.put("carts", carts);
			orderMap.add(map);
		}
		
		return orderMap;
	}
	
	protected String getMeetingGoodsPictureUrl(String folder) {
		return orderPlatformDir() + configUtils.getOrderPlatformApp().getMeetingGoods() + "/" + folder + "/";
	}

	@Override
	public Integer totalOrderByAreaSeq(Integer areaSeq,Integer meetingSeq) {
		
		return meetingShoppingCartDao.totalOrderByAreaSeq(areaSeq, meetingSeq);
	}

	@Override
	public List<Map<String, Object>> getUserRankByAreaSeq(Integer areaSeq, Integer meetingSeq) {
		//订货方列表
		
		
		return meetingShoppingCartDao.getUserRankByAreaSeq(areaSeq, meetingSeq);
	}

	@Override
	public Integer totalOrderByCategorySeq(Integer categorySeq, Integer meetingSeq) {
		
		return meetingOrderDao.totalOrderByCategorySeq(categorySeq, meetingSeq);
	}

	@Override
	public List<Map<String, Object>> getCategoryRank(Integer meetingSeq, Integer categorySeq) {
		List<Map<String, Object>> categoryRank=meetingOrderDao.getCategoryRank(categorySeq, meetingSeq);
		for (Map<String, Object> map : categoryRank) {
			Integer categorySeq2=(Integer) map.get("seq");
			GoodsCategoryEntity goodsCategoryEntity=goodsCategoryDao.selectById(categorySeq2);
			map.put("categoryName", goodsCategoryEntity.getName());
		}
		return categoryRank;
	}

	@Override
	public List<Map<String, Object>> getCategoryRankByArea(Integer meetingSeq, Integer categorySeq, Integer areaSeq) {
		List<Map<String, Object>> categoryRank=meetingOrderDao.getCategoryRankByArea(categorySeq, meetingSeq, areaSeq);
		for (Map<String, Object> map : categoryRank) {
			Integer categorySeq2=(Integer) map.get("seq");
			GoodsCategoryEntity goodsCategoryEntity=goodsCategoryDao.selectById(categorySeq2);
			map.put("categoryName", goodsCategoryEntity.getName());
		}
		return categoryRank;
	}

	@Override
	public List<Map<String, Object>> getCategoryRankByUser(Integer meetingSeq, Integer categorySeq, Integer userSeq) {
		List<Map<String, Object>> categoryRank=meetingOrderDao.getCategoryRankByUser(categorySeq, meetingSeq, userSeq);
		for (Map<String, Object> map : categoryRank) {
			Integer categorySeq2=(Integer) map.get("seq");
			GoodsCategoryEntity goodsCategoryEntity=goodsCategoryDao.selectById(categorySeq2);
			map.put("categoryName", goodsCategoryEntity.getName());
		}
		return categoryRank;
	}

	@Override
	public List<Map<String, Object>> getUserRankByCategorySeq(Integer meetingSeq, Integer categorySeq) {
		List<Map<String, Object>>  userMaps=meetingShoppingCartDao.getUserRankByCategorySeq(meetingSeq, categorySeq);
		for (Map<String, Object> map : userMaps) {
			Integer userSeq=(Integer) map.get("userSeq");
			BaseUserEntity baseUserEntity=baseUserDao.selectById(userSeq);
			map.put("baseUserEntity", baseUserEntity);
		}
		return userMaps;
	}

	@Override
	public List<Map<String, Object>> getAreaRankByCategorySeq(Integer meetingSeq, Integer categorySeq) {
		List<Map<String, Object>> areaMaps=meetingShoppingCartDao.getAreaRankByCategorySeq(meetingSeq, categorySeq);
		for (Map<String, Object> map : areaMaps) {
			Integer areaSeq=(Integer) map.get("areaSeq");
			MeetingAreaEntity meetingAreaEntity=meetingAreaDao.selectById(areaSeq);
			map.put("meetingAreaEntity", meetingAreaEntity);
		}
		return areaMaps;
	}

	@Override
	public List<Map<String, Object>> getRankByCategorySeq(Integer meetingSeq, Integer categorySeq) {
		return meetingGoodsDao.getRankByCategorySeq(meetingSeq,categorySeq);
	}

	@Override
	public List<Map<String, Object>> getNumRankByCategorySeq(Integer meetingSeq, Integer categorySeq) {
		return meetingGoodsDao.getNumRankByCategorySeq(meetingSeq,categorySeq);
	}

	
	/**
	 * 选款次数排行（购物车 + 订单购物车   count本货号数据条数）
	 */
	@Override
	public Integer getPickNumRank(Integer meetingSeq, Integer meetingGoodsSeq) {
		List<Map<String, Object>> rankList = meetingRankDao.getPickNumRankList(meetingSeq);
		for(Map<String, Object> map : rankList) {
			Integer seq = (Integer)map.get("meetingGoodsSeq");
			if(meetingGoodsSeq.equals(seq)) {
				return ((Long)map.get("rank")).intValue();
			}
		}
		return null;
	}

	
	/**
	 * 订货量排行（购物车已配码 + 订单   sum订货数量）
	 */
	@Override
	public Integer getOrderNumRank(Integer meetingSeq, Integer meetingGoodsSeq) {
		List<Map<String, Object>> rankList = meetingRankDao.getOrderNumRankList(meetingSeq);
		for(Map<String, Object> map : rankList) {
			Integer seq = (Integer)map.get("meetingGoodsSeq");
			if(meetingGoodsSeq.equals(seq)) {
				return ((Long)map.get("rank")).intValue();
			}
		}
		return null;
	}
	
	
	
	/**
	 * 货号在同品类所有货品中选款次数排行（购物车 + 订单购物车  count本货号数据条数）
	 */
	@Override
	public Integer getCategoryPickNumRank(Integer meetingSeq, Integer meetingGoodsSeq, Integer categorySeq) {
		List<Map<String, Object>> rankList = meetingRankDao.getCategoryPickNumRankList(meetingSeq, categorySeq);
		for(Map<String, Object> map : rankList) {
			Integer seq = (Integer)map.get("meetingGoodsSeq");
			if(meetingGoodsSeq.equals(seq)) {
				return ((Long)map.get("rank")).intValue();
			}
		}
		return null;
	}
	
	
	
	/**
	 * 货号在同品类所有货品中订货量排行（购物车已配码 + 订单   sum订货数量）
	 */
	@Override
	public Integer getCategoryOrderNumRank(Integer meetingSeq, Integer meetingGoodsSeq, Integer categorySeq) {
		List<Map<String, Object>> rankList = meetingRankDao.getCategoryOrderNumRankList(meetingSeq, categorySeq);
		for(Map<String, Object> map : rankList) {
			Integer seq = (Integer)map.get("meetingGoodsSeq");
			if(meetingGoodsSeq.equals(seq)) {
				return ((Long)map.get("rank")).intValue();
			}
		}
		return null;
	}
	

}
