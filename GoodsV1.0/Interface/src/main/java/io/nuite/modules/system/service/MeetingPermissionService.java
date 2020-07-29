package io.nuite.modules.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.system.entity.MeetingPermissionEntity;


public interface MeetingPermissionService extends IService<MeetingPermissionEntity> {

	List<Object> getMyMeetingSeqList(Integer userSeq);


	MeetingPermissionEntity getMeetingPermission(Integer userSeq, Integer meetingSeq);


	public void getAllPermissionByMeetingSeq(Integer meetingSeq,List<Integer> seqs, List<Integer> allSeqs);

}

