package io.nuite.modules.order_platform_app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDetailEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;

public interface MeetingOrderExcelService {

	
	List<MeetingShoppingCartDetailEntity> getMeetingShoppingCartDetailListByShoppingCartSeq(Integer meetingShoppingCartSeq);
	
	
	Integer submitMeetingOrder(List<Integer> meetingShoppingCartSeqs, MeetingOrderEntity orderEntity,
							   List<MeetingOrderProductEntity> orderProductList, Date nowDate, BaseUserEntity baseUserEntity) throws Exception;


	List<MeetingOrderEntity> getCompanyMeetingOrderList(Integer meetingSeq, Integer start, Integer num);


	List<MeetingOrderEntity> getUserMeetingOrderList(Integer userSeq, Integer meetingSeq, Integer start, Integer num);


	List<MeetingOrderProductEntity> getMeetingOrderProductListByMeetingOrderSeq(Integer meetingOrderSeq);


	Map<String, Object> getMeetingOrderMapBySeq(Integer meetingOrderSeq);


	List<Object> getMeetingOrderGoodsSeqList(Integer meetingOrderSeq);


	List<Map<String, Object>> getMeetingOrderGoodsColorDetailList(Integer meetingOrderSeq, Integer meetingGoodsSeq);


	List<Map<String, Object>> getMeetingOrderGoodsColorSizeNumDetailList(Integer meetingOrderSeq, Integer meetingGoodsSeq,
			Integer colorSeq);


	Map<String, Object> getUserMapByUserSeq(Integer userSeq);

	/**
	 * 订单列表(订货会订单和订货平台订单合并)
	 * @param meetingSeq
	 * @param userSeq
	 * @param companySeq
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<MeetingOrderEntity> getOrderList(Integer meetingSeq, Integer userSeq, Integer companySeq,Integer type, Page<MeetingOrderEntity> page) throws Exception;
}
