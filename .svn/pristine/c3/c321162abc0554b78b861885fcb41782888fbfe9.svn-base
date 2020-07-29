package io.nuite.modules.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.system.dao.MeetintUserAreaDao;
import io.nuite.modules.system.entity.MeetingPermissionEntity;
import io.nuite.modules.system.entity.MeetintUserAreaEntity;
import io.nuite.modules.system.service.MeetintUserAreaService;


@Service
public class MeetintUserAreaServiceImpl extends ServiceImpl<MeetintUserAreaDao, MeetintUserAreaEntity> implements MeetintUserAreaService {

	@Autowired
	private MeetintUserAreaDao meetintUserAreaDao;
	
	
	@Override
	public void getAllUserByMeetingSeq(Integer areaSeq, List<Integer> seqs, List<Integer> allSeqs) {
		List<MeetintUserAreaEntity> meetintUserAreas=new ArrayList<MeetintUserAreaEntity>();
		for (Integer userSeq : allSeqs) {
			MeetintUserAreaEntity meetintUserArea=meetintUserAreaDao.getArea(areaSeq, userSeq);
			if(meetintUserArea!=null) {
				meetintUserAreas.add(meetintUserArea);
			}
		}
		for (MeetintUserAreaEntity meetintUserAreaEntity : meetintUserAreas) {
			Integer userSeq=meetintUserAreaEntity.getUserSeq();
			if(seqs==null) {
				meetintUserAreaDao.deleteById(meetintUserAreaEntity);
			}else {
				if(!seqs.contains(userSeq)) {
					meetintUserAreaDao.deleteById(meetintUserAreaEntity);
				}
			}
			
		}
		if(seqs!=null) {
			for (Integer userSeq : seqs) {
				//判断当前userSeq是否存在于Permssion表中
				MeetintUserAreaEntity meetintUserArea=meetintUserAreaDao.getArea(areaSeq, userSeq);
				if(meetintUserArea!=null) {
					System.out.println(meetintUserArea.getDel());
					meetintUserArea.setDel(0);
					meetintUserAreaDao.update(meetintUserArea);
				}else {
					meetintUserArea=new MeetintUserAreaEntity();
					meetintUserArea.setDel(0);
					meetintUserArea.setUserSeq(userSeq);
					meetintUserArea.setMeetingAreaSeq(areaSeq);
					meetintUserAreaDao.insert(meetintUserArea);
				}
			}
		}
		
	}

}
