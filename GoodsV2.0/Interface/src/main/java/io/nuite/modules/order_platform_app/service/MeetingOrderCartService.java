package io.nuite.modules.order_platform_app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartEntity;


public interface MeetingOrderCartService extends IService<MeetingOrderCartEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    
    List<MeetingOrderCartEntity> getListByMeetingSeq(Integer meetingSeq);
	
	List<Object> getUserListByMeetingSeq(Integer meetingSeq);
	
	List<MeetingOrderCartEntity> getListByOrderSeq(Integer OrderSeq);

    /**
     * 获取订单详情中每个货号的总价
     * @param goodId
     * @param meetingOrderSeq
     * @return
     * @throws Exception
     */
    BigDecimal selectOrderDetailMoney(String goodId,Integer meetingOrderSeq) throws Exception;

    /**
     * 获取订单总价
     * @param meetingOrderSeq
     * @return
     * @throws Exception
     */
    BigDecimal selectOrderMoney(Integer meetingOrderSeq) throws Exception;
}

