package io.nuite.modules.order_platform_app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-04-16 10:12:39
 */
@Mapper
public interface MeetingRankDao {

	// 选款次数排行（购物车 + 订单购物车   count本货号数据条数）
	List<Map<String, Object>> getPickNumRankList(@Param("meetingSeq")Integer meetingSeq);
	
	// 订货量排行（购物车已配码 + 订单   sum订货数量）
	List<Map<String, Object>> getOrderNumRankList(@Param("meetingSeq")Integer meetingSeq);
	
	// 货号在同品类所有货品中选款次数排行（购物车 + 订单购物车  count本货号数据条数）
	List<Map<String, Object>> getCategoryPickNumRankList(@Param("meetingSeq")Integer meetingSeq, @Param("categorySeq")Integer categorySeq);
	
	// 货号在同品类所有货品中订货量排行（购物车已配码 + 订单   sum订货数量）
	List<Map<String, Object>> getCategoryOrderNumRankList(@Param("meetingSeq")Integer meetingSeq, @Param("categorySeq")Integer categorySeq);
	
}
