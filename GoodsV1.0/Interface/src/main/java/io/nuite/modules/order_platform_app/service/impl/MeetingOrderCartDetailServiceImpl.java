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
import io.nuite.modules.order_platform_app.dao.MeetingOrderCartDetailDao;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDetailEntity;
import io.nuite.modules.order_platform_app.service.MeetingOrderCartDetailService;


@Service
public class MeetingOrderCartDetailServiceImpl extends ServiceImpl<MeetingOrderCartDetailDao, MeetingOrderCartDetailEntity> implements MeetingOrderCartDetailService {

	@Autowired
	private MeetingOrderCartDetailDao MeetingOrderCartDetailDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MeetingOrderCartDetailEntity> page = this.selectPage(
                new Query<MeetingOrderCartDetailEntity>(params).getPage(),
                new EntityWrapper<MeetingOrderCartDetailEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<MeetingOrderCartDetailEntity> getListByMeetingShoppingCartDistributeBoxSeq(
			Integer meetingShoppingCartDistributeBoxSeq) {
		Wrapper<MeetingOrderCartDetailEntity> wrapper = new EntityWrapper<MeetingOrderCartDetailEntity>();
		wrapper.where("MeetingOrderCartDistributeBox_Seq = {0} ", meetingShoppingCartDistributeBoxSeq);
		return MeetingOrderCartDetailDao.selectList(wrapper);
	}

	@Override
	public List<MeetingOrderCartDetailEntity> getListByMeetingShoppingCartSeq(Integer meetingShoppingCartSeq) {
		Wrapper<MeetingOrderCartDetailEntity> wrapper = new EntityWrapper<MeetingOrderCartDetailEntity>();
		wrapper.where("MeetingOrderCart_Seq = {0} ", meetingShoppingCartSeq);
		return MeetingOrderCartDetailDao.selectList(wrapper);
	}

	@Override
	public List<Map<String, Object>> getSelectNum(Integer meetingShoppingCartSeq) {
		Wrapper<MeetingOrderCartDetailEntity> wrapper = new EntityWrapper<MeetingOrderCartDetailEntity>();
		wrapper.setSqlSelect("Color_Seq AS colorSeq, SUM (SelectNum) AS num").where("MeetingOrderCart_Seq = {0}", meetingShoppingCartSeq).groupBy("Color_Seq");
		return MeetingOrderCartDetailDao.selectMaps(wrapper);
	}
}
