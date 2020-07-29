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
import io.nuite.modules.system.dao.MeetingAreaDao;
import io.nuite.modules.system.dao.MeetintUserAreaDao;
import io.nuite.modules.system.entity.MeetingAreaEntity;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.entity.MeetintUserAreaEntity;
import io.nuite.modules.system.service.MeetingAreaService;


@Service
public class MeetingAreaServiceImpl extends ServiceImpl<MeetingAreaDao, MeetingAreaEntity> implements MeetingAreaService {

	@Autowired
	private MeetingAreaDao meetingAreaDao;
	
	@Autowired
	private MeetintUserAreaDao meetintUserAreaDao;
	
	/**
	 * 查询公司所有订货区域
	 */
	@Override
	public List<MeetingAreaEntity> getCompanyAllMeetingArea(Integer companySeq) {
		Wrapper<MeetingAreaEntity> wrapper = new EntityWrapper<MeetingAreaEntity>();
		wrapper.where("Company_Seq = {0}", companySeq).orderBy("Seq ASC");
		return meetingAreaDao.selectList(wrapper);
	}


    @Override
    public PageUtils queryPage(Map<String, Object> params, Integer companySeq) {
        Page<MeetingAreaEntity> page = super.selectPage(
            new Query<MeetingAreaEntity>(params).getPage(),
            new EntityWrapper<MeetingAreaEntity>().eq("Company_Seq", companySeq).orderBy("InputTime",false)
        );
        return new PageUtils(page);
    }


	@Override
	public List<MeetingAreaEntity> getMeetingAreaByName(String name) {
		return meetingAreaDao.getMeetingAreaByName(name);
	}


	@Override
	public Boolean hasAreaInUsers(Integer areaSeq) {
		Wrapper<MeetintUserAreaEntity> wrapper=new EntityWrapper<MeetintUserAreaEntity>();
		wrapper.where("MeetingArea_Seq ={0}", areaSeq);
		List<MeetintUserAreaEntity> list=meetintUserAreaDao.selectList(wrapper);
		if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
	}
}
