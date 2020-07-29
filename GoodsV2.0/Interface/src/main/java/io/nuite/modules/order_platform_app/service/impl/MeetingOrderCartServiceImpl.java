package io.nuite.modules.order_platform_app.service.impl;

import java.math.BigDecimal;
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
import io.nuite.modules.order_platform_app.dao.MeetingOrderCartDao;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartEntity;
import io.nuite.modules.order_platform_app.service.MeetingOrderCartService;


@Service
public class MeetingOrderCartServiceImpl extends ServiceImpl<MeetingOrderCartDao, MeetingOrderCartEntity> implements MeetingOrderCartService {

	@Autowired
	private MeetingOrderCartDao meetingOrderCartDao;
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MeetingOrderCartEntity> page = this.selectPage(
                new Query<MeetingOrderCartEntity>(params).getPage(),
                new EntityWrapper<MeetingOrderCartEntity>()
        );

        return new PageUtils(page);
    }
    
    @Override
	public List<MeetingOrderCartEntity> getListByMeetingSeq(Integer meetingSeq) {
		Wrapper<MeetingOrderCartEntity> wrapper = new EntityWrapper<MeetingOrderCartEntity>();
		wrapper.where("Meeting_Seq = {0} ", meetingSeq);
		return meetingOrderCartDao.selectList(wrapper);
	}


	@Override
	public List<Object> getUserListByMeetingSeq(Integer meetingSeq) {
		Wrapper<MeetingOrderCartEntity> wrapper = new EntityWrapper<MeetingOrderCartEntity>();
		wrapper.setSqlSelect("User_Seq AS userSeq").where("Meeting_Seq = {0}", meetingSeq).groupBy("User_Seq");
		return meetingOrderCartDao.selectObjs(wrapper);
	}


	@Override
	public List<MeetingOrderCartEntity> getListByOrderSeq(Integer OrderSeq) {
		Wrapper<MeetingOrderCartEntity> wrapper = new EntityWrapper<MeetingOrderCartEntity>();
		wrapper.where("MeetingOrderSeq = {0}", OrderSeq);
		return meetingOrderCartDao.selectList(wrapper);
	}

	@Override
	public BigDecimal selectOrderDetailMoney(String goodId,Integer meetingOrderSeq) throws Exception {
    	Map<String,Object> map = new HashMap<>(10);
    	map.put("goodId",goodId);
    	map.put("meetingOrderSeq",meetingOrderSeq);
		return meetingOrderCartDao.selectOrderDetailMoney(map);
	}

	@Override
	public BigDecimal selectOrderMoney(Integer meetingOrderSeq) throws Exception {
		Map<String,Object> map = new HashMap<>(10);
		map.put("meetingOrderSeq",meetingOrderSeq);
		return meetingOrderCartDao.selectOrderMoney(map);
	}

}
