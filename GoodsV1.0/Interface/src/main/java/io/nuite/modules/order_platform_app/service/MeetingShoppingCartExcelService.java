package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDistributeBoxEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartEntity;


public interface MeetingShoppingCartExcelService extends IService<MeetingShoppingCartDistributeBoxEntity> {

	MeetingShoppingCartEntity getUserMeetingShoppingCart(Integer userSeq, Integer meetingGoodsSeq);
	
	List<Map<String, Object>> getAllUsersSelectNum(Integer meetingGoodsSeq);
	
	List<MeetingShoppingCartDistributeBoxEntity> getMeetingShoppingCartDistributeBoxListByShoppingCartSeq(Integer meetingShoppingCartSeq);

	void sizeAllot(Integer meetingShoppingCartSeq, String userGoodId, Integer perBoxNum, String sizeAllotDetail);

	Integer getUserShoppingCartSelectNum(Integer distributeBoxSeq, Integer size);

}

