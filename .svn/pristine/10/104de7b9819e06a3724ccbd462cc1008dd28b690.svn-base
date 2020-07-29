package io.nuite.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.order_platform_app.dao.MeetingStatisticsModelDao;
import io.nuite.modules.order_platform_app.entity.MeetingStatisticsModelEntity;
import io.nuite.modules.system.service.MeetingStatisticsModelService;

@Service
public class MeetingStatisticsModelServiceImpl extends ServiceImpl<MeetingStatisticsModelDao, MeetingStatisticsModelEntity> implements MeetingStatisticsModelService {

    @Autowired
    private MeetingStatisticsModelDao meetingStatisticsModelDao;
    

    
    /**
     * 全部模板list
     */
	@Override
	public List<MeetingStatisticsModelEntity> getMeetingStatisticsModel(Integer companySeq) {
        Wrapper<MeetingStatisticsModelEntity> wrapper = new EntityWrapper<MeetingStatisticsModelEntity>();
        wrapper.where("Company_Seq = {0}", companySeq);
        return meetingStatisticsModelDao.selectList(wrapper);
	}
	
	
	
    /**
     * 分页模板list
     */
    @Override
    public Page<MeetingStatisticsModelEntity> getMeetingStatisticsModelPage(Integer companySeq, Integer pageNum, Integer pageSize) {
        Wrapper<MeetingStatisticsModelEntity> wrapper = new EntityWrapper<MeetingStatisticsModelEntity>();
        wrapper.where("Company_Seq = {0}", companySeq);
        Page<MeetingStatisticsModelEntity> page = new Page<MeetingStatisticsModelEntity>(pageNum, pageSize);
        return this.selectPage(page, wrapper);
    }


	
    /**
     * 判断模板是否已存在
     */
    @Override
    public boolean modelNameExisted(Integer seq, Integer companySeq, String modelName) {
        Wrapper<MeetingStatisticsModelEntity> wrapper = new EntityWrapper<MeetingStatisticsModelEntity>();
        wrapper.where("Company_Seq = {0} AND ModelName = {1}", companySeq, modelName);
        List<MeetingStatisticsModelEntity> list = meetingStatisticsModelDao.selectList(wrapper);
        if (list.size() == 0) {
            return false;
        } else if (list.size() == 1){
            if (seq != null) { //修改，判断seq是否相同
                if (list.get(0).getSeq().equals(seq)) {
                    return false;  //相同，返回false
                } else {
                    return true;
                }
            } else { //新增，返回true
                return true;
            }
        } else {
        	return true;
        }
    }



    /**
     * 新增模板
     */
	@Override
	public Integer addMeetingStatisticsModel(MeetingStatisticsModelEntity meetingStatisticsModelEntity) {
		meetingStatisticsModelDao.insert(meetingStatisticsModelEntity);
		return meetingStatisticsModelEntity.getSeq();
	}


	/**
	 * 修改模板
	 */
	@Override
	public void updateMeetingStatisticsModel(MeetingStatisticsModelEntity meetingStatisticsModelEntity) {
		meetingStatisticsModelDao.updateById(meetingStatisticsModelEntity);
	}


	/**
	 * 删除模板
	 */
	@Override
	public void deleteMeetingStatisticsModel(Integer seq) {
		meetingStatisticsModelDao.deleteById(seq);
	}

}
