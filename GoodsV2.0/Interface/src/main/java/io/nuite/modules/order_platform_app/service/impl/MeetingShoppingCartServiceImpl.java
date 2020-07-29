package io.nuite.modules.order_platform_app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Joiner;

import io.nuite.modules.order_platform_app.dao.MeetingOrderCartDao;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDao;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDetailDao;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDistributeBoxDao;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDetailEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDistributeBoxEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartEntity;
import io.nuite.modules.order_platform_app.service.MeetingShoppingCartService;
import io.nuite.modules.system.dao.MeetingOrderDao;
import io.nuite.modules.system.dao.MeetingOrderProductDao;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;


@Service
public class MeetingShoppingCartServiceImpl extends ServiceImpl<MeetingShoppingCartDao, MeetingShoppingCartEntity> implements MeetingShoppingCartService {

	@Autowired
	private MeetingShoppingCartDao meetingShoppingCartDao;
	
    @Autowired
    private MeetingShoppingCartDetailDao meetingShoppingCartDetailDao;
    
    @Autowired
    private MeetingOrderDao meetingOrderDao;
    
    @Autowired
    private MeetingOrderProductDao meetingOrderProductDao;
    
    @Autowired
    private MeetingOrderCartDao meetingOrderCartDao;
    
    @Autowired
    private MeetingShoppingCartDistributeBoxDao meetingShoppingCartDistributeBoxDao;
    
	
	
	/**
	 * 查询货号所有用户已提交订单总量
	 */
	@Override
	public List<Map<String, Object>> getAllUsersBuyNum(Integer meetingGoodsSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.setSqlSelect("Color_Seq AS colorSeq, SUM (BuyCount) AS num").where("MeetingGoods_Seq = {0} AND Cancel = 0", meetingGoodsSeq).groupBy("Color_Seq");
		return meetingOrderProductDao.selectMaps(wrapper);
	}


	/**
	 * 我的购物车中已选数量
	 */
	@Override
	public Integer getMySelectNum(Integer userSeq, Integer meetingGoodsSeq) {
		MeetingShoppingCartEntity meetingShoppingCartEntity = new MeetingShoppingCartEntity();
		meetingShoppingCartEntity.setUserSeq(userSeq);
		meetingShoppingCartEntity.setMeetingGoodsSeq(meetingGoodsSeq);
		meetingShoppingCartEntity = meetingShoppingCartDao.selectOne(meetingShoppingCartEntity);
		if(meetingShoppingCartEntity != null && meetingShoppingCartEntity.getTotalSelectNum() != null) {
			return meetingShoppingCartEntity.getTotalSelectNum();
		} else {
			return 0;
		}
	}
	
	
	
	/**
	 * 我的购物车中所有货品已配数量Map
	 */
	@Override
	public Map<Integer, Integer> getMySelectNumMap(Integer userSeq, Integer meetingSeq) {
		Wrapper<MeetingShoppingCartEntity> wrapper = new EntityWrapper<MeetingShoppingCartEntity>();
		wrapper.where("Meeting_Seq = {0} AND User_Seq ={1}", meetingSeq, userSeq);
		List<MeetingShoppingCartEntity> list = meetingShoppingCartDao.selectList(wrapper);
		Map<Integer, Integer> mySelectNumMap =  new HashMap<Integer, Integer>();
		for (MeetingShoppingCartEntity meetingShoppingCartEntity : list) {
			if(meetingShoppingCartEntity.getTotalSelectNum() != null) {
				mySelectNumMap.put(meetingShoppingCartEntity.getMeetingGoodsSeq(), meetingShoppingCartEntity.getTotalSelectNum());
			} else {
				mySelectNumMap.put(meetingShoppingCartEntity.getMeetingGoodsSeq(), 0);
			}
		}
		return mySelectNumMap;
	}
	
	
	/**
	 * 我的订单中已定数量
	 */
	@Override
	public Integer getMyBuyNum(Integer userSeq, Integer meetingSeq, Integer meetingGoodsSeq) {
		
		Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<MeetingOrderEntity>();
		wrapper.setSqlSelect("Seq")
		.where("Meeting_Seq = {0} AND User_Seq ={1}", meetingSeq, userSeq);
		List<Object> orderSeqList = meetingOrderDao.selectObjs(wrapper);
		
		if(orderSeqList != null && orderSeqList.size() > 0) {
			Wrapper<MeetingOrderProductEntity> productWrapper = new EntityWrapper<MeetingOrderProductEntity>();
			productWrapper.setSqlSelect("SUM (BuyCount) AS num").in("MeetingOrder_Seq", orderSeqList)
			.where("MeetingGoods_Seq = {0} AND Cancel = 0", meetingGoodsSeq);
			List<Object> list = meetingOrderProductDao.selectObjs(productWrapper);
			if(list != null && list.size() > 0 && list.get(0) != null) {
				return (Integer) list.get(0);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
		
	}

	
	
	/**
	 * 我的订单中所有货品已定数量Map
	 */
	@Override
	public Map<Integer, Integer> getMyBuyNumMap(Integer userSeq, Integer meetingSeq) {
		Map<Integer, Integer> myBuyNumMap =  new HashMap<Integer, Integer>();
		
		Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<MeetingOrderEntity>();
		wrapper.setSqlSelect("Seq")
		.where("Meeting_Seq = {0} AND User_Seq ={1}", meetingSeq, userSeq);
		List<Object> orderSeqList = meetingOrderDao.selectObjs(wrapper);
		
		if(orderSeqList != null && orderSeqList.size() > 0) {
			Wrapper<MeetingOrderProductEntity> productWrapper = new EntityWrapper<MeetingOrderProductEntity>();
			productWrapper.setSqlSelect("MeetingGoods_Seq AS meetingGoodsSeq, SUM (BuyCount) AS num").in("MeetingOrder_Seq", orderSeqList)
			.where("Cancel = 0").groupBy("MeetingGoods_Seq");
			List<Map<String, Object>> list = meetingOrderProductDao.selectMaps(productWrapper);
			
			for (Map<String, Object> map : list) {
				myBuyNumMap.put((Integer)map.get("meetingGoodsSeq"), (Integer)map.get("num"));
			}
		}
		
		return myBuyNumMap;
	}
	
	
	
	/**
	 * 我的购物车中所有货品已选次数Map
	 */
	@Override
	public Map<Integer, Integer> getMySelectCountMap(Integer userSeq, Integer meetingSeq) {
		Wrapper<MeetingShoppingCartEntity> wrapper = new EntityWrapper<MeetingShoppingCartEntity>();
		wrapper.where("Meeting_Seq = {0} AND User_Seq ={1}", meetingSeq, userSeq);
		List<MeetingShoppingCartEntity> list = meetingShoppingCartDao.selectList(wrapper);
		Map<Integer, Integer> mySelectCountMap =  new HashMap<Integer, Integer>();
		for (MeetingShoppingCartEntity meetingShoppingCartEntity : list) {
			mySelectCountMap.put(meetingShoppingCartEntity.getMeetingGoodsSeq(), 1);
		}
		return mySelectCountMap;
	}
	
	
	
	
	/**
	 * 我的订单中所有货品已定次数Map（理论上一个订单只有一次）
	 */
	@Override
	public Map<Integer, Integer> getMyBuyCountMap(Integer userSeq, Integer meetingSeq) {
		Map<Integer, Integer> myBuyCountMap =  new HashMap<Integer, Integer>();
		
		Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<MeetingOrderEntity>();
		wrapper.setSqlSelect("Seq")
		.where("Meeting_Seq = {0} AND User_Seq ={1}", meetingSeq, userSeq);
		List<Object> orderSeqList = meetingOrderDao.selectObjs(wrapper);
		
		if(orderSeqList != null && orderSeqList.size() > 0) {
			Wrapper<MeetingOrderProductEntity> productWrapper = new EntityWrapper<MeetingOrderProductEntity>();
			productWrapper.setSqlSelect("MeetingGoods_Seq AS meetingGoodsSeq").in("MeetingOrder_Seq", orderSeqList)
			.where("Cancel = 0").groupBy("MeetingOrder_Seq, MeetingGoods_Seq");
			List<Object> list = meetingOrderProductDao.selectObjs(productWrapper);
			for (Object meetingGoodsSeq : list) {
				if(myBuyCountMap.containsKey((Integer)meetingGoodsSeq)) {
					myBuyCountMap.put((Integer)meetingGoodsSeq, myBuyCountMap.get((Integer)meetingGoodsSeq) + 1);
				} else {
					myBuyCountMap.put((Integer)meetingGoodsSeq, 1);
				}
			}
		}
		
		return myBuyCountMap;
	}
	
	

	/**
	 * 查询用户是否加入此货号到购物车
	 */
	@Override
	public MeetingShoppingCartEntity getUserMeetingShoppingCart(Integer userSeq, Integer meetingGoodsSeq) {
		MeetingShoppingCartEntity meetingShoppingCartEntity = new MeetingShoppingCartEntity();
		meetingShoppingCartEntity.setUserSeq(userSeq);
		meetingShoppingCartEntity.setMeetingGoodsSeq(meetingGoodsSeq);
		return meetingShoppingCartDao.selectOne(meetingShoppingCartEntity);
	}



	
	/**
	 * 购物车列表
	 */
	@Override
	public List<MeetingShoppingCartEntity> getMeetingShoppingCartList(Integer userSeq, Integer meetingSeq) {
		Wrapper<MeetingShoppingCartEntity> wrapper = new EntityWrapper<MeetingShoppingCartEntity>();
		wrapper.where("Meeting_Seq = {0} AND User_Seq = {1}", meetingSeq, userSeq).orderBy("IsAllocated ASC");
		return meetingShoppingCartDao.selectList(wrapper);
	}


	/**
	 * 购物车全选、全不选
	 */
	@Override
	public void checkAllShoppingCart(Integer meetingSeq, Integer userSeq, Integer isChecked) {
		MeetingShoppingCartEntity meetingShoppingCartEntity = new MeetingShoppingCartEntity();
		meetingShoppingCartEntity.setIsChecked(isChecked);
		
		Wrapper<MeetingShoppingCartEntity> wrapper = new EntityWrapper<MeetingShoppingCartEntity>();
		wrapper.where("Meeting_Seq = {0} AND User_Seq = {1} ", meetingSeq, userSeq);
		meetingShoppingCartDao.update(meetingShoppingCartEntity, wrapper);
		
	}


	/**
	 * 批量删除购物车
	 */
	@Override
	public void deleteMeetingShoppingCart(List<Integer> meetingShoppingCartSeqs) {
		//1.删除购物车
		meetingShoppingCartDao.deleteBatchIds(meetingShoppingCartSeqs);
		//2.删除对应购物车详情
		Wrapper<MeetingShoppingCartDetailEntity> wrapper = new EntityWrapper<MeetingShoppingCartDetailEntity>();
		wrapper.in("MeetingShoppingCart_Seq", meetingShoppingCartSeqs);
		meetingShoppingCartDetailDao.delete(wrapper);
		//3).删除购物车配箱详情
		Wrapper<MeetingShoppingCartDistributeBoxEntity> wrapper2 = new EntityWrapper<MeetingShoppingCartDistributeBoxEntity>();
		wrapper2.in("MeetingShoppingCart_Seq", meetingShoppingCartSeqs);
		meetingShoppingCartDistributeBoxDao.delete(wrapper2);
	}



	@Override
	public List<Map<String, Object>> getGoodsRank(Integer meetingSeq,Integer showType) {
		return meetingShoppingCartDao.getMeetingGoodsIdRank(meetingSeq,showType);
	}


	@Override
	public List<Map<String, Object>> getAreaRank(List<Object> meetingGoodsSeqList,Integer showType) {
		String meetingGoodsSeqs = Joiner.on(",").join(meetingGoodsSeqList);
		return meetingShoppingCartDao.getMeetingGoodsAreaRank(meetingGoodsSeqs,showType);
	}


	@Override
	public List<Map<String, Object>> getUserRank(Integer meetingSeq,Integer showType) {
		return meetingShoppingCartDao.getUserRank(meetingSeq,showType);
	}


	@Override
	public Integer getMeetingGoodsNum(Integer meetingSeq) {
		Wrapper<MeetingShoppingCartEntity> wrapper = new EntityWrapper<MeetingShoppingCartEntity>();
		wrapper.setSqlSelect("MeetingGoods_Seq").where("Meeting_Seq = {0}", meetingSeq).groupBy("MeetingGoods_Seq");
		List<Map<String, Object>> list=meetingShoppingCartDao.selectMaps(wrapper);
		return list.size();
	}


	@Override
	public Integer getMeetingGoodsCount(Integer meetingSeq) {
		Wrapper<MeetingOrderCartEntity> wrapper = new EntityWrapper<MeetingOrderCartEntity>();
		wrapper.setSqlSelect("MeetingGoods_Seq").where("Meeting_Seq = {0}", meetingSeq).groupBy("MeetingGoods_Seq");
		List<Map<String, Object>> list=meetingOrderCartDao.selectMaps(wrapper);
		return list.size();
	}

	@Override
	public Integer selectPickNum(Integer companySeq, Integer meetingSeq, Integer userSeq, String keywords) throws Exception {
		Map<String,Object> map = new HashMap<>(10);
		map.put("companySeq",companySeq);
		map.put("meetingSeq",meetingSeq);
		map.put("userSeq",userSeq);
		map.put("keywords",keywords);
		return meetingShoppingCartDao.selectPickNum(map);
	}

}
