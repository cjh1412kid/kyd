package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDetailEntity;


public interface MeetingOrderCartDetailService extends IService<MeetingOrderCartDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<MeetingOrderCartDetailEntity> getListByMeetingShoppingCartDistributeBoxSeq(Integer meetingShoppingCartDistributeBoxSeq);

	List<MeetingOrderCartDetailEntity> getListByMeetingShoppingCartSeq(Integer meetingShoppingCartSeq);
	
	List<Map<String, Object>> getSelectNum(Integer meetingShoppingCartSeq);
}

