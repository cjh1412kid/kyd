package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

public interface MeetingRankService {

	List<Map<String, Object>> getMeetingGoodsAreaRank(List<Object> meetingGoodsSeqList);

	List<Map<String, Object>> getMeetingGoodsIdRank(Integer meetingSeq, Integer areaSeq);
	

	List<Map<String, Object>> getMeetingGoodsNumRank(Integer meetingSeq);

	Map<String, Object> totalData(Integer meetingSeq);
	
	List<Map<String, Object>> getAreaRank(List<Object> meetingGoodsSeqList,Integer totalNum);

	List<Map<String, Object>> getGoodsIdRank(Integer meetingSeq,Integer totalNum);
	
	List<Map<String, Object>> getUserRank(Integer meetingSeq,Integer totalNum);
	
	Integer totalOrderKindByUser(Integer meetingSeq,Integer userSeq);
	
	Integer totalOrderKindByArea(Integer meetingSeq,Integer areaSeq);
	
	Integer totalOrderByGoodsSeq(Integer meetingGoodsSeq);
	
	List<Map<String, Object>> getUserRankByGoodsSeq(Integer meetingGoodsSeq);
	
	List<Map<String, Object>> getAreaRankByGoodsSeq(Integer meetingGoodsSeq);
	
	Integer totalOrderByUserSeq(Integer userSeq,Integer meetingSeq);
	
	Integer totalOrderKindByUserSeq(Integer userSeq,Integer meetingSeq);
	
	List<Map<String, Object>> getGoodRankByUserSeq(Integer userSeq,Integer meetingSeq);
	
	Integer totalOrderByAreaSeq(Integer areaSeq,Integer meetingSeq);
	
	List<Map<String, Object>> getUserRankByAreaSeq(Integer areaSeq,Integer meetingSeq);
	
	Integer totalOrderByCategorySeq(Integer categorySeq,Integer meetingSeq);
	
	List<Map<String, Object>> getCategoryRank(Integer meetingSeq,Integer categorySeq);
	
	List<Map<String, Object>> getCategoryRankByArea(Integer meetingSeq,Integer categorySeq,Integer areaSeq);
	
	List<Map<String, Object>> getCategoryRankByUser(Integer meetingSeq,Integer categorySeq,Integer userSeq);
	
	List<Map<String, Object>> getUserRankByCategorySeq(Integer meetingSeq,Integer categorySeq);
	
	List<Map<String, Object>> getAreaRankByCategorySeq(Integer meetingSeq,Integer categorySeq);
	
	List<Map<String, Object>> getRankByCategorySeq(Integer meetingSeq,Integer categorySeq);
	
	List<Map<String, Object>> getNumRankByCategorySeq(Integer meetingSeq,Integer categorySeq);

	Integer getPickNumRank(Integer meetingSeq, Integer meetingGoodsSeq);

	Integer getOrderNumRank(Integer meetingSeq, Integer meetingGoodsSeq);

	Integer getCategoryPickNumRank(Integer meetingSeq, Integer meetingGoodsSeq, Integer categorySeq);

	Integer getCategoryOrderNumRank(Integer meetingSeq, Integer meetingGoodsSeq, Integer categorySeq);
	
}
