package io.nuite.modules.online_sales_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.online_sales_app.entity.OrderCartEntity;


public interface OrderCartService extends IService<OrderCartEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取订单详情
     * @param orderSeq
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectOrderCart(Integer orderSeq) throws Exception;

    /**
     * 按货品获取订单详情
     * @param companySeq
     * @param periodSeq
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectOrderByGood(Integer companySeq,Integer periodSeq) throws Exception;

    /**
     * 按生产厂家获取订单详情
     * @param companySeq
     * @param periodSeq
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectOrderByFactory(Integer companySeq,Integer periodSeq) throws Exception;
    List<Map<String, Object>> getCartDetail(Integer orderSeq);

    /**
     * 按类别获取订单详情
     * @param companySeq
     * @param periodSeq
     * @param categorySeq
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectOrderByCategory(Integer companySeq,Integer periodSeq,Integer categorySeq) throws Exception;

 
    Integer getCountByShoesSeq(Integer shoesSeq ,Integer userSeq);


    /**
     * 根据波次获取订单车
     * @param periodSeq
     * @return
     * @throws Exception
     */
    List<OrderCartEntity> selectOrderCartByPeriod(Integer periodSeq) throws Exception;

    /**
     * 获取最小尺码
     * @param periodSeq
     * @return
     * @throws Exception
     */
    String selectMinSizeByPeriod(Integer periodSeq) throws Exception;

    /**
     * 获取最大尺码
     * @param periodSeq
     * @return
     * @throws Exception
     */
    String selectMaxSizeByPeriod(Integer periodSeq) throws Exception;
 }


