package io.nuite.modules.order_platform_app.service;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.order_platform_app.entity.MeetingGoodsColorImgsEntity;


public interface MeetingGoodsColorImgsService extends IService<MeetingGoodsColorImgsEntity> {

	MeetingGoodsColorImgsEntity getColorImgsEntityByMeetingGoodsAndColorSeq(Integer meetingGoodsSeq, Integer colorSeq);

}

