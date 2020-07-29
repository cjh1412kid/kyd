package io.nuite.modules.order_platform_app.dao;

import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-04-18 09:30:08
 */
@Mapper
public interface MeetingShoppingCartDao extends BaseMapper<MeetingShoppingCartEntity> {
	
	  List<Map<String, Object>> getAreaRank(@Param("meetingGoodsSeqs")String meetingGoodsSeqs);
	  
	  List<Map<String, Object>> getMeetingGoodsIdRank(@Param("meetingSeq")Integer meetingSeq,@Param("showType")Integer showType);
	  
	  List<Map<String, Object>> getMeetingGoodsAreaRank(@Param("meetingGoodsSeqs")String meetingGoodsSeqs,@Param("showType")Integer showType);
	  
	  List<Map<String, Object>> getUserRank(@Param("meetingSeq")Integer meetingSeq,@Param("showType")Integer showType);
	  
	  Integer totalOrderProduct(@Param("meetingSeq")Integer meetingSeq);
	  
	  Integer totalOrder(@Param("meetingSeq")Integer meetingSeq);
	  
	  Integer totalOrderKind(@Param("meetingSeq")Integer meetingSeq);
	  
	  Integer totalOrderUser(@Param("meetingSeq")Integer meetingSeq);
	  
	  Integer totalOrderKindByUser(@Param("meetingSeq")Integer meetingSeq,@Param("userSeq")Integer userSeq);
	  
	  Integer totalOrderKindByArea(@Param("meetingSeq")Integer meetingSeq,@Param("areaSeq")Integer areaSeq);
	  
	  Integer totalOrderByGoodsSeq(@Param("meetingGoodsSeq")Integer meetingGoodsSeq);
	  
	  List<Map<String, Object>> getUserRankByGoodsSeq(@Param("meetingGoodsSeq")Integer meetingGoodsSeq);
	  
	  List<Map<String, Object>> getUserDetailByGoodsSeq(@Param("meetingGoodsSeq")Integer meetingGoodsSeq,@Param("userSeq")Integer userSeq);

	  List<Map<String, Object>> getAreaRankByGoodsSeq(@Param("meetingGoodsSeq")Integer meetingGoodsSeq);
	  
	  List<Map<String, Object>> getAreaDetailByGoodsSeq(@Param("meetingGoodsSeq")Integer meetingGoodsSeq,@Param("areaSeq")Integer areaSeq);

	  Integer totalOrderByUserSeq(@Param("userSeq")Integer userSeq,@Param("meetingSeq")Integer meetingSeq);
		
		Integer totalOrderKindByUserSeq(@Param("userSeq")Integer userSeq,@Param("meetingSeq")Integer meetingSeq);
		
		Integer totalOrderByAreaSeq(@Param("areaSeq")Integer areaSeq,@Param("meetingSeq")Integer meetingSeq);
		
		List<Map<String, Object>> getUserRankByAreaSeq(@Param("areaSeq")Integer areaSeq,@Param("meetingSeq")Integer meetingSeq);
		
		Integer totalOrderByCategorySeq(@Param("meetingSeq")Integer meetingSeq,@Param("categorySeq")Integer categorySeq);
		
		List<Map<String, Object>> getUserRankByCategorySeq(@Param("meetingSeq")Integer meetingSeq,@Param("categorySeq")Integer categorySeq);

		List<Map<String, Object>> getAreaRankByCategorySeq(@Param("meetingSeq")Integer meetingSeq,@Param("categorySeq")Integer categorySeq);

	/**
	 * 获取选款数量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Integer selectPickNum(Map<String,Object> map) throws Exception;
}
