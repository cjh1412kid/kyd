package io.nuite.modules.order_platform_app.service.impl;

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
import io.nuite.modules.order_platform_app.dao.MeetingOrderCartDistributeBoxDao;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDistributeBoxEntity;
import io.nuite.modules.order_platform_app.service.MeetingOrderCartDistributeBoxService;


@Service
public class MeetingOrderCartDistributeBoxServiceImpl extends ServiceImpl<MeetingOrderCartDistributeBoxDao, MeetingOrderCartDistributeBoxEntity> implements MeetingOrderCartDistributeBoxService {

	@Autowired
	private MeetingOrderCartDistributeBoxDao meetingOrderCartDistributeBoxDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MeetingOrderCartDistributeBoxEntity> page = this.selectPage(
                new Query<MeetingOrderCartDistributeBoxEntity>(params).getPage(),
                new EntityWrapper<MeetingOrderCartDistributeBoxEntity>()
        );

        return new PageUtils(page);
    }

    @Override
	public List<Map<String, Object>> getSelectNum(Integer meetingShoppingCartSeq) {
		Wrapper<MeetingOrderCartDistributeBoxEntity> wrapper = new EntityWrapper<MeetingOrderCartDistributeBoxEntity>();
		wrapper.setSqlSelect("Color_Seq AS colorSeq, SUM (ColorTotalNum) AS num").where("MeetingOrderCart_Seq = {0}", meetingShoppingCartSeq).groupBy("Color_Seq");
		return meetingOrderCartDistributeBoxDao.selectMaps(wrapper);
	}
    
    @Override
	public List<MeetingOrderCartDistributeBoxEntity> getMeetingShoppingCartDistributeBoxListByShoppingCartSeq(Integer meetingShoppingCartSeq) {
		Wrapper<MeetingOrderCartDistributeBoxEntity> wrapper = new EntityWrapper<MeetingOrderCartDistributeBoxEntity>();
		wrapper.where("MeetingOrderCart_Seq = {0}", meetingShoppingCartSeq);
		return meetingOrderCartDistributeBoxDao.selectList(wrapper);
	}

	@Override
	public List<Map<String, Object>> getTotalNumByMeetingSeq(Integer meetingSeq) {
		return meetingOrderCartDistributeBoxDao.getTotalNumByMeetingSeq(meetingSeq);
	}

}
