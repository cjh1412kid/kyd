package io.nuite.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.common.utils.PageUtils;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.entity.MeetingEntity;

import java.util.List;
import java.util.Map;


public interface MeetingService extends IService<MeetingEntity> {

    PageUtils queryPage(Map<String, Object> params,Integer companySeq);
    
    List<Object> getAllYear(Integer companySeq);
    
	List<Object> getMyMeetingAllYear(List<Object> myMeetingSeqList);

	List<MeetingEntity> getMeetingListByCompanySeq(Integer companySeq, Integer year);

	MeetingEntity getNowMeetingEntity(Integer companySeq);

	List<MeetingEntity> getMyMeetingList(List<Object> myMeetingSeqList, Integer year);

	Map getUserMeetings(BaseUserEntity userEntity);
	
	List<MeetingEntity> getMeetingListByCompanySeq(Integer companySeq);
	
	MeetingEntity getNearMeetingEntity(Integer companySeq);
	
	Boolean hasMeetingInMeetingGoods(Integer meetingSeq);
}

