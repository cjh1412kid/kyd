package io.nuite.modules.order_platform_app.service;

import java.util.Date;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.order_platform_app.entity.MeetingUserIllegalActsEntity;


public interface MeetingUserIllegalActsService extends IService<MeetingUserIllegalActsEntity> {

	Integer getUserIllegalActsState(Integer userSeq);

	MeetingUserIllegalActsEntity getLastUserIllegalActsEntity(Integer userSeq);

	Date getUserLastScanInTime(Integer userSeq, Integer meetingSeq);

}

