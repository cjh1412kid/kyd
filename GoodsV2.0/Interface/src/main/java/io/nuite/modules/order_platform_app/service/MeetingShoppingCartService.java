package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartEntity;


public interface MeetingShoppingCartService extends IService<MeetingShoppingCartEntity> {

	List<Map<String, Object>> getAllUsersBuyNum(Integer meetingGoodsSeq);
	
	
	Integer getMySelectNum(Integer userSeq, Integer meetingGoodsSeq);
	
	Map<Integer, Integer> getMySelectNumMap(Integer userSeq, Integer meetingSeq);
	
	Integer getMyBuyNum(Integer userSeq, Integer meetingSeq, Integer meetingGoodsSeq);

	Map<Integer, Integer> getMyBuyNumMap(Integer userSeq, Integer meetingSeq);
	
	
	Map<Integer, Integer> getMySelectCountMap(Integer userSeq, Integer meetingSeq);

	Map<Integer, Integer> getMyBuyCountMap(Integer userSeq, Integer meetingSeq);
	
	
	
	
	MeetingShoppingCartEntity getUserMeetingShoppingCart(Integer userSeq, Integer meetingGoodsSeq);

	List<MeetingShoppingCartEntity> getMeetingShoppingCartList(Integer userSeq, Integer meetingSeq);

	void checkAllShoppingCart(Integer meetingSeq, Integer userSeq, Integer isChecked);

	void deleteMeetingShoppingCart(List<Integer> meetingShoppingCartSeqs);
	
	List<Map<String, Object>> getGoodsRank(Integer meetingSeq,Integer showType);
	
	List<Map<String, Object>> getAreaRank(List<Object> meetingGoodsSeqs,Integer showType);
	
	List<Map<String, Object>> getUserRank(Integer meetingSeq,Integer showType);
	
	Integer getMeetingGoodsNum(Integer meetingSeq);
	
	Integer getMeetingGoodsCount(Integer meetingSeq);

	/**
	 * 获取选款数量
	 * @param companySeq
	 * @param meetingSeq
	 * @param userSeq
	 * @param keywords
	 * @return
	 * @throws Exception
	 */
	Integer selectPickNum(Integer companySeq,Integer meetingSeq,Integer userSeq,String keywords) throws Exception;

}

