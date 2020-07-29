package io.nuite.modules.order_platform_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.order_platform_app.dao.MeetingGoodsColorImgsDao;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsColorImgsEntity;
import io.nuite.modules.order_platform_app.service.MeetingGoodsColorImgsService;


@Service
public class MeetingGoodsColorImgsServiceImpl extends ServiceImpl<MeetingGoodsColorImgsDao, MeetingGoodsColorImgsEntity> implements MeetingGoodsColorImgsService {

	@Autowired
    private MeetingGoodsColorImgsDao meetingGoodsColorImgsDao;

	
	/**
	 * 获取货品颜色实体
	 */
	@Override
	public MeetingGoodsColorImgsEntity getColorImgsEntityByMeetingGoodsAndColorSeq(Integer meetingGoodsSeq, Integer colorSeq) {
		MeetingGoodsColorImgsEntity meetingGoodsColorImgsEntity = new MeetingGoodsColorImgsEntity();
		meetingGoodsColorImgsEntity.setMeetingGoodsSeq(meetingGoodsSeq);
		meetingGoodsColorImgsEntity.setColorSeq(colorSeq);
		return meetingGoodsColorImgsDao.selectOne(meetingGoodsColorImgsEntity);
	}
	
}
