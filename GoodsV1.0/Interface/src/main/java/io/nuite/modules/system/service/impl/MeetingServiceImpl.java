package io.nuite.modules.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import io.nuite.modules.order_platform_app.dao.MeetingGoodsDao;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.dao.MeetingDao;
import io.nuite.modules.system.dao.MeetingPermissionDao;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.entity.MeetingPermissionEntity;
import io.nuite.modules.system.service.MeetingService;

@Service
public class MeetingServiceImpl extends ServiceImpl<MeetingDao, MeetingEntity> implements MeetingService {
	
    @Autowired
    private MeetingDao meetingDao;
    
    @Autowired
    private MeetingGoodsDao meetingGoodsDao;

    @Autowired
    private MeetingPermissionDao meetingPermissionDao;
    
    @Override
    public PageUtils queryPage(Map<String, Object> params, Integer companySeq) {
        Page<MeetingEntity> page = super.selectPage(
            new Query<MeetingEntity>(params).getPage(),
            new EntityWrapper<MeetingEntity>().eq("CompanySeq", companySeq).orderBy("InputTime",false)
        );
        
        return new PageUtils(page);
    }

    
    
    /**
     * 查询所有年份
     */
	@Override
	public List<Object> getAllYear(Integer companySeq) {
		Wrapper<MeetingEntity> wrapper = new EntityWrapper<MeetingEntity>();
		wrapper.setSqlSelect("DISTINCT Year").where("CompanySeq = {0}", companySeq).orderBy("Year DESC");
		return meetingDao.selectObjs(wrapper);
	}
	
	
	
    /**
     * 查询所有年份
     */
	@Override
	public List<Object> getMyMeetingAllYear(List<Object> myMeetingSeqList) {
		if(myMeetingSeqList != null && myMeetingSeqList.size() > 0) {
			Wrapper<MeetingEntity> wrapper = new EntityWrapper<MeetingEntity>();
			wrapper.setSqlSelect("DISTINCT Year").in("Seq", myMeetingSeqList).orderBy("Year DESC");
			return meetingDao.selectObjs(wrapper);
		} else {
			return new ArrayList<Object>();
		}
	}
	
	
	
    
    /**
     * 根据公司序号获取所有订货会
     */
	@Override
	public List<MeetingEntity> getMeetingListByCompanySeq(Integer companySeq, Integer year) {
		Wrapper<MeetingEntity> wrapper = new EntityWrapper<MeetingEntity>();
		wrapper.where("CompanySeq = {0} AND Year = {1}", companySeq, year).orderBy("StartTime DESC");
		return meetingDao.selectList(wrapper);
	}



	/**
	 * 获取当前正在进行的订货会
	 */
	@Override
	public MeetingEntity getNowMeetingEntity(Integer companySeq) {
		Wrapper<MeetingEntity> wrapper = new EntityWrapper<MeetingEntity>();
		wrapper.where("CompanySeq = {0} AND StartTime <= {1} AND EndTime >= {1}", companySeq, new Date());
		List<MeetingEntity> list = meetingDao.selectList(wrapper);
		if(list != null && list.size() > 0 && list.get(0) != null) {
			return list.get(0);
		}
		return null;
	}



	/**
	 * 查询我的订货会列表
	 */
	@Override
	public List<MeetingEntity> getMyMeetingList(List<Object> myMeetingSeqList, Integer year) {
		if(myMeetingSeqList != null && myMeetingSeqList.size() > 0) {
			Wrapper<MeetingEntity> wrapper = new EntityWrapper<MeetingEntity>();
			wrapper.in("Seq", myMeetingSeqList).where("Year = {0}", year).orderBy("StartTime DESC");
			return meetingDao.selectList(wrapper);
		} else {
			return new ArrayList<MeetingEntity>();
		}
	}



	@Override
	public Map<String, List<MeetingEntity>> getUserMeetings(BaseUserEntity userEntity) {
		  EntityWrapper<MeetingEntity> ew = new EntityWrapper<MeetingEntity>();
	        ew.eq("CompanySeq", userEntity.getCompanySeq()).eq("Del", 0).orderDesc(Collections.singletonList("InputTime"));
	        List<MeetingEntity> meetings = meetingDao.selectList(ew);
	        Map<String, List<MeetingEntity>> meetingMap = new HashMap<String, List<MeetingEntity>>();
	        for (MeetingEntity meetingEntity : meetings) {
	            String year = meetingEntity.getYear().toString();
	            List<MeetingEntity> list = meetingMap.computeIfAbsent(year, k -> new ArrayList<>());
	            list.add(meetingEntity);
	        }
	        return meetingMap;
	}



	@Override
	public List<MeetingEntity> getMeetingListByCompanySeq(Integer companySeq) {
		Wrapper<MeetingEntity> wrapper = new EntityWrapper<MeetingEntity>();
		wrapper.where("CompanySeq = {0}", companySeq).orderBy("StartTime DESC");
		return meetingDao.selectList(wrapper);
	}



	@Override
	public MeetingEntity getNearMeetingEntity(Integer companySeq) {
		Wrapper<MeetingEntity> wrapper = new EntityWrapper<MeetingEntity>();
		wrapper.where("CompanySeq = {0}", companySeq).orderBy("Year DESC,StartTime DESC");
		List<MeetingEntity> list = meetingDao.selectList(wrapper);
		if(list != null && list.size() > 0 && list.get(0) != null) {
			return list.get(0);
		}
		return null;
	}



	@Override
	public Boolean hasMeetingInMeetingGoods(Integer meetingSeq) {
		Wrapper<MeetingGoodsEntity> wrapper=new EntityWrapper<MeetingGoodsEntity>();
		wrapper.where("Meeting_Seq ={0}", meetingSeq);
		List<MeetingGoodsEntity> list=meetingGoodsDao.selectList(wrapper);
		
		Wrapper<MeetingPermissionEntity> wrapper2=new EntityWrapper<MeetingPermissionEntity>();
		wrapper2.where("Meeting_Seq = {0}", meetingSeq);
		
		List<MeetingPermissionEntity> list2=meetingPermissionDao.selectList(wrapper2);
		
		if ((list != null && list.size() > 0)||(list2 != null && list2.size() > 0)) {
            return true;
        } else {
            return false;
        }
	}
	
    
}
