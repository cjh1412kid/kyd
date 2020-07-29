package io.nuite.modules.online_sales_app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.online_sales_app.entity.OrderCartDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-06-25 09:20:40
 */
@Mapper
public interface OrderCartDetailDao extends BaseMapper<OrderCartDetailEntity> {
    /**
     * 获取尺码
     * @param map
     * @return
     * @throws Exception
     */
    String selectSize(Map<String,Object> map) throws Exception;

    /**
     * 获取尺码详情
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectSizeNum(Map<String,Object> map) throws Exception;
}
