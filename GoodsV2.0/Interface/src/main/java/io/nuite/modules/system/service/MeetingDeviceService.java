package io.nuite.modules.system.service;


import java.util.List;


import java.util.Map;


import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.system.entity.MeetingDeviceEntity;


public interface MeetingDeviceService extends IService<MeetingDeviceEntity> {


	List<MeetingDeviceEntity> getAllMeetingDevice(Integer type,Integer companySeq);


	PageUtils queryPage(Map<String, Object> params,Integer companySeq);

}

