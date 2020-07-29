package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.order_platform_app.entity.MeetingGoodsValuateEntity;


public interface MeetingGoodsValuateService extends IService<MeetingGoodsValuateEntity> {

	List<Map<String, Object>> getMeetingGoodsValuateList(Integer meetingGoodsSeq, Integer start, Integer num);

	String getMeetingGoodsValuateByUserSeq(Integer meetingGoodsSeq,Integer userSeq);
}

