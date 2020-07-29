package io.nuite.modules.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.system.entity.MeetingAreaEntity;


public interface MeetingAreaService extends IService<MeetingAreaEntity> {

	List<MeetingAreaEntity> getCompanyAllMeetingArea(Integer companySeq);

	 PageUtils queryPage(Map<String, Object> params,Integer companySeq);
	 
	 List<MeetingAreaEntity> getMeetingAreaByName(String name);
	 
	 Boolean hasAreaInUsers(Integer areaSeq);
}

