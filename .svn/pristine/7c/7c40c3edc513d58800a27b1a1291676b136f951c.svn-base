package io.nuite.modules.order_platform_app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.order_platform_app.dao.MeetingUserIllegalActsDao;
import io.nuite.modules.order_platform_app.entity.MeetingUserIllegalActsEntity;
import io.nuite.modules.order_platform_app.service.MeetingUserIllegalActsService;


@Service
public class MeetingUserIllegalActsServiceImpl extends ServiceImpl<MeetingUserIllegalActsDao, MeetingUserIllegalActsEntity> implements MeetingUserIllegalActsService {
	
	
	@Autowired
	private MeetingUserIllegalActsDao meetingUserIllegalActsDao;
	
	
	
	/**
	 * 查询用户非法行为记录状态(0:正常 1:锁定)
	 */
	@Override
	public Integer getUserIllegalActsState(Integer userSeq) {
		Wrapper<MeetingUserIllegalActsEntity> wrapper = new EntityWrapper<MeetingUserIllegalActsEntity>();
		wrapper.where("User_Seq = {0} AND State = 1", userSeq);
		List<MeetingUserIllegalActsEntity> list = meetingUserIllegalActsDao.selectList(wrapper);
		if(list != null && list.size() > 0 && list.get(0) != null) {
			return 1;
		} else {
			return 0;
		}
	}
	
	
	
	
	/**
	 * 获取用户某次订货会最后一次扫码入场的时间
	 */
	@Override
	public Date getUserLastScanInTime(Integer userSeq, Integer meetingSeq) {
		Wrapper<MeetingUserIllegalActsEntity> wrapper = new EntityWrapper<MeetingUserIllegalActsEntity>();
		wrapper.where("User_Seq = {0} AND Meeting_Seq = {1} ", userSeq).orderBy("InputTime DESC");
		List<MeetingUserIllegalActsEntity> list = meetingUserIllegalActsDao.selectList(wrapper);
		if(list != null && list.size() > 0 && list.get(0) != null) {
			return list.get(0).getInputTime();
		} else {
			return null;
		}
	}
	
	
	
	/**
	 * 查询用户最后一条行为记录
	 */
	@Override
	public MeetingUserIllegalActsEntity getLastUserIllegalActsEntity(Integer userSeq) {
		Wrapper<MeetingUserIllegalActsEntity> wrapper = new EntityWrapper<MeetingUserIllegalActsEntity>();
		wrapper.where("User_Seq = {0}", userSeq).orderBy("InputTime Desc");
		List<MeetingUserIllegalActsEntity> list = meetingUserIllegalActsDao.selectList(wrapper);
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	

}
