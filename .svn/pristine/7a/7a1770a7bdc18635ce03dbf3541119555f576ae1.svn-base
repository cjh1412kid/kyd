package io.nuite.modules.order_platform_app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Joiner;

import io.nuite.modules.order_platform_app.dao.MeetingUserSizeAllotCodeHistoryDao;
import io.nuite.modules.order_platform_app.entity.MeetingUserSizeAllotCodeHistoryEntity;
import io.nuite.modules.order_platform_app.service.MeetingUserSizeAllotCodeHistoryService;


@Service
public class MeetingUserSizeAllotCodeHistoryServiceImpl extends ServiceImpl<MeetingUserSizeAllotCodeHistoryDao, MeetingUserSizeAllotCodeHistoryEntity> implements MeetingUserSizeAllotCodeHistoryService {

	
	@Autowired
	private MeetingUserSizeAllotCodeHistoryDao meetingUserSizeAllotCodeHistoryDao;
	
	
	
	/**
	 * 根据最小最大尺码查询用户的配码代码历史
	 */
	@Override
	public List<MeetingUserSizeAllotCodeHistoryEntity> getUserSizeAllotHistoryList(Integer userSeq, Integer minSize, Integer maxSize) {
		Wrapper<MeetingUserSizeAllotCodeHistoryEntity> wrapper = new EntityWrapper<MeetingUserSizeAllotCodeHistoryEntity>();
		wrapper.where("User_Seq = {0}", userSeq);
		if(minSize != null) {
			wrapper.where("MinSize = {0}", minSize);
		}
		if(maxSize != null) {
			wrapper.where("MaxSize = {0}", maxSize);
		}
		wrapper.orderBy("InputTime Desc");
		return meetingUserSizeAllotCodeHistoryDao.selectList(wrapper);
	}



	/**
	 * 新增一条配码代码历史记录
	 */
	@Override
	public void addUserSizeAllotCodeHistory(Integer userSeq, Integer minSize, Integer maxSize, List<Integer> sizeAllotCode) {
		MeetingUserSizeAllotCodeHistoryEntity meetingUserSizeAllotCodeHistoryEntity = new MeetingUserSizeAllotCodeHistoryEntity();
		meetingUserSizeAllotCodeHistoryEntity.setUserSeq(userSeq);
		meetingUserSizeAllotCodeHistoryEntity.setMinSize(minSize);
		meetingUserSizeAllotCodeHistoryEntity.setMaxSize(maxSize);
		meetingUserSizeAllotCodeHistoryEntity.setSizeAllotCode(Joiner.on(",").join(sizeAllotCode));
		//查询是否已存在完全相同的配码代码
		MeetingUserSizeAllotCodeHistoryEntity existEntity = meetingUserSizeAllotCodeHistoryDao.selectOne(meetingUserSizeAllotCodeHistoryEntity);
		if(existEntity == null) {
			meetingUserSizeAllotCodeHistoryEntity.setInputTime(new Date());
			meetingUserSizeAllotCodeHistoryEntity.setDel(0);
			meetingUserSizeAllotCodeHistoryDao.insert(meetingUserSizeAllotCodeHistoryEntity);
		} else {
			existEntity.setInputTime(new Date());
			meetingUserSizeAllotCodeHistoryDao.updateById(existEntity);
		}
	}

}
