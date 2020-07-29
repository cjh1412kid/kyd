package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDetailEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;

public interface MeetingOrderService extends IService<MeetingOrderEntity> {

	
	List<MeetingShoppingCartDetailEntity> getMeetingShoppingCartDetailListByShoppingCartSeq(Integer meetingShoppingCartSeq);
	
	
	Integer submitMeetingOrder(List<Integer> meetingShoppingCartSeqs, MeetingOrderEntity orderEntity,
			List<MeetingOrderProductEntity> orderProductList);


	List<MeetingOrderEntity> getCompanyMeetingOrderList(Integer meetingSeq, Integer start, Integer num);


	List<MeetingOrderEntity> getUserMeetingOrderList(Integer userSeq, Integer meetingSeq, Integer start, Integer num);


	List<MeetingOrderProductEntity> getMeetingOrderProductListByMeetingOrderSeq(Integer meetingOrderSeq);


	Map<String, Object> getMeetingOrderMapBySeq(Integer meetingOrderSeq);


	List<Object> getMeetingOrderGoodsSeqList(Integer meetingOrderSeq);


	List<Map<String, Object>> getMeetingOrderGoodsColorDetailList(Integer meetingOrderSeq, Integer meetingGoodsSeq);


	List<Map<String, Object>> getMeetingOrderGoodsColorSizeNumDetailList(Integer meetingOrderSeq, Integer meetingGoodsSeq,
			Integer colorSeq);


	Map<String, Object> getUserMapByUserSeq(Integer userSeq);
	
	List<MeetingOrderProductEntity> getListByMeetingGoodsSeq(Integer meetingGoodsSeq);

	List<BaseUserEntity> getAllUserInMeeting(Integer meetingSeq);
}
