package io.nuite.modules.online_sales_app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.online_sales_app.entity.OrderCartDistributeBoxEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-06-25 09:17:48
 */
@Mapper
public interface OrderCartDistributeBoxDao extends BaseMapper<OrderCartDistributeBoxEntity> {
    /**
     * 获取颜色详情
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectColorNumByPeriod(Map<String,Object> map) throws Exception;
}
