package io.nuite.modules.online_sales_app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.online_sales_app.entity.OrderCartEntity;

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
public interface OrderCartDao extends BaseMapper<OrderCartEntity> {
    /**
     * 获取订单详情
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectOrderCart(Map<String,Object> map) throws Exception;

    /**
     * 获取颜色详情
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectColor(Map<String,Object> map) throws Exception;

    /**
     * 获取尺码列表
     * @param map
     * @return
     * @throws Exception
     */
    List<Integer> selectSize(Map<String,Object> map) throws Exception;

    /**
     * 获取尺码详情
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectGoodsColorNum(Map<String,Object> map) throws Exception;

    /**
     * 按货品获取订单详情
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectOrderByGood(Map<String,Object> map) throws Exception;

    /**
     * 获取生产厂家
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectFactory(Map<String,Object> map) throws Exception;

    /**
     * 按生产厂家
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectOrderByFactory(Map<String,Object> map) throws Exception;

    /**
     * 获取类别
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectCategory(Map<String,Object> map) throws Exception;

    /**
     * 按类别获取订单详情
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectOrderByCategory(Map<String,Object> map) throws Exception;

    /**
     * 根据波次获取订单车
     * @param map
     * @return
     * @throws Exception
     */
    List<OrderCartEntity> selectOrderCartByPeriod(Map<String,Object> map) throws Exception;

    /**
     * 获取最小尺码
     * @param map
     * @return
     * @throws Exception
     */
    String selectMinSizeByPeriod(Map<String,Object> map) throws Exception;

    /**
     * 获取最大尺码
     * @param map
     * @return
     * @throws Exception
     */
    String selectMaxSizeByPeriod(Map<String,Object> map) throws Exception;
}
