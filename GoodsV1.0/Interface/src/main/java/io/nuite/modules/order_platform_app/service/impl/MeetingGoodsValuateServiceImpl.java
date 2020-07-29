package io.nuite.modules.order_platform_app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.order_platform_app.dao.MeetingGoodsValuateDao;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsValuateEntity;
import io.nuite.modules.order_platform_app.service.MeetingGoodsValuateService;


@Service
public class MeetingGoodsValuateServiceImpl extends ServiceImpl<MeetingGoodsValuateDao, MeetingGoodsValuateEntity> implements MeetingGoodsValuateService {
    
    @Autowired
    private MeetingGoodsValuateDao meetingGoodsValuateDao;
	
	
	/**
	 * 查询订货会商品评价列表
	 */
	@Override
	public List<Map<String, Object>> getMeetingGoodsValuateList(Integer meetingGoodsSeq, Integer start, Integer num) {
		Wrapper<MeetingGoodsValuateEntity> wrapper = new EntityWrapper<MeetingGoodsValuateEntity>();
		wrapper.setSqlSelect("Seq AS seq, User_Seq AS userSeq, MeetingGoods_Seq AS meetingGoodsSeq, Suggest AS suggest, InputTime AS inputTime")
		.where("MeetingGoods_Seq = {0}", meetingGoodsSeq).orderBy("InputTime DESC");
		return meetingGoodsValuateDao.selectMapsPage(new RowBounds(start - 1, num), wrapper);
	}


	@Override
	public String getMeetingGoodsValuateByUserSeq(Integer meetingGoodsSeq, Integer userSeq) {
		  Map<String, Object> params=new HashMap<String, Object>();
		  params.put("MeetingGoods_Seq",meetingGoodsSeq);
          params.put("User_Seq",userSeq);
		   List<MeetingGoodsValuateEntity> valuates=meetingGoodsValuateDao.selectByMap(params);
           String valuate="";
           for (MeetingGoodsValuateEntity meetingGoodsValuateEntity : valuates) {
           	if(valuate=="") {
           		valuate=meetingGoodsValuateEntity.getSuggest();
           	}else {
           		valuate=valuate+";"+meetingGoodsValuateEntity.getSuggest();
           	}
			}
		return valuate;
	}
	
}
