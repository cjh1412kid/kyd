package io.nuite.modules.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.system.dao.MeetingPermissionDao;
import io.nuite.modules.system.entity.MeetingPermissionEntity;
import io.nuite.modules.system.service.MeetingPermissionService;



@Service
public class MeetingPermissionServiceImpl extends ServiceImpl<MeetingPermissionDao, MeetingPermissionEntity> implements MeetingPermissionService {

	@Autowired
	private MeetingPermissionDao meetingPermissionDao;
	
	
	
	/**
	 * 查询我参加过的订货会序号
	 */
	@Override
	public List<Object> getMyMeetingSeqList(Integer userSeq) {
		Wrapper<MeetingPermissionEntity> wrapper = new EntityWrapper<MeetingPermissionEntity>();
		wrapper.setSqlSelect("Meeting_Seq").where("User_Seq = {0}", userSeq);
		return meetingPermissionDao.selectObjs(wrapper);
	}




	/**
	 * 查询用户订货会权限
	 */
	@Override
	public MeetingPermissionEntity getMeetingPermission(Integer userSeq, Integer meetingSeq) {
		MeetingPermissionEntity meetingPermissionEntity = new MeetingPermissionEntity();
		meetingPermissionEntity.setUserSeq(userSeq);
		meetingPermissionEntity.setMeetingSeq(meetingSeq);
		return meetingPermissionDao.selectOne(meetingPermissionEntity);
	}



	@Override
	public void getAllPermissionByMeetingSeq(Integer meetingSeq,List<Integer> seqs,List<Integer> allSeqs) {
		List<MeetingPermissionEntity> permissions=new ArrayList<MeetingPermissionEntity>();

		for (Integer userSeq : allSeqs) {
			MeetingPermissionEntity permission=meetingPermissionDao.getPermission(meetingSeq, userSeq);
			if(permission!=null) {
				permissions.add(permission);
			}
		}
		for (MeetingPermissionEntity meetingPermissionEntity : permissions) {
			Integer userSeq=meetingPermissionEntity.getUserSeq();
			if(seqs==null) {
				meetingPermissionDao.deleteById(meetingPermissionEntity);
			}else {
				if(!seqs.contains(userSeq)) {
					meetingPermissionDao.deleteById(meetingPermissionEntity);
				}
			}
			
		}
		if(seqs!=null) {
			for (Integer userSeq : seqs) {
				//判断当前userSeq是否存在于Permssion表中
				MeetingPermissionEntity permission=meetingPermissionDao.getPermission(meetingSeq, userSeq);
				if(permission!=null) {
					System.out.println(permission.getDel());
					permission.setDel(0);
					meetingPermissionDao.update(permission);
				}else {
					permission=new MeetingPermissionEntity();
					permission.setDel(0);
					permission.setUserSeq(userSeq);
					permission.setMeetingSeq(meetingSeq);
					meetingPermissionDao.insert(permission);
				}
			}
		}
		
	}

}
