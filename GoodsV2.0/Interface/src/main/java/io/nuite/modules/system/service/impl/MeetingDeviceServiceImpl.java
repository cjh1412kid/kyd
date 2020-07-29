package io.nuite.modules.system.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.modules.system.dao.MeetingDeviceDao;
import io.nuite.modules.system.entity.MeetingDeviceEntity;
import io.nuite.modules.system.service.MeetingDeviceService;


@Service
public class MeetingDeviceServiceImpl extends ServiceImpl<MeetingDeviceDao, MeetingDeviceEntity> implements MeetingDeviceService {


	@Autowired
	private MeetingDeviceDao meetingDeviceDao;
	
	
	
	/**
	 * 获取设备列表
	 */
	@Override
	public List<MeetingDeviceEntity> getAllMeetingDevice(Integer type,Integer companySeq) {
		Wrapper<MeetingDeviceEntity> wrapper = new EntityWrapper<MeetingDeviceEntity>();
		wrapper.where("DeviceType = {0}", type).eq("CompanySeq",companySeq);
		return meetingDeviceDao.selectList(wrapper);
	}


	
	@Override
	public PageUtils queryPage(Map<String, Object> params, Integer companySeq) {
		 Page<MeetingDeviceEntity> page = super.selectPage(
		            new Query<MeetingDeviceEntity>(params).getPage(),
		            new EntityWrapper<MeetingDeviceEntity>().eq("CompanySeq", companySeq).orderBy("InputTime",false)
		   );
		 return new PageUtils(page);
	}

}
