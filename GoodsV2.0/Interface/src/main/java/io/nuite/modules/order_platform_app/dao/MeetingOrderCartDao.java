package io.nuite.modules.order_platform_app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.order_platform_app.entity.MeetingOrderCartEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-05-31 15:09:26
 */
@Mapper
public interface MeetingOrderCartDao extends BaseMapper<MeetingOrderCartEntity> {
    /**
     * 获取订单详情中每个货号的总价
     * @param map
     * @return
     * @throws Exception
     */
    BigDecimal selectOrderDetailMoney(Map<String,Object> map) throws Exception;

    /**
     * 获取订单总价
     * @param map
     * @return
     * @throws Exception
     */
    BigDecimal selectOrderMoney(Map<String,Object> map) throws Exception;
}
