package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDistributeBoxEntity;


public interface MeetingOrderCartDistributeBoxService extends IService<MeetingOrderCartDistributeBoxEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<Map<String, Object>> getSelectNum(Integer meetingShoppingCartSeq);
    
	List<MeetingOrderCartDistributeBoxEntity> getMeetingShoppingCartDistributeBoxListByShoppingCartSeq(Integer meetingShoppingCartSeq);

	List<Map<String, Object>> getTotalNumByMeetingSeq(Integer meetingSeq);
}

