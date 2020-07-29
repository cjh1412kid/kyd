package io.nuite.modules.online_sales_app.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.OrderCartEntity;
import io.nuite.modules.online_sales_app.entity.OrderEntity;
import io.nuite.modules.online_sales_app.entity.OrderProductsEntity;
import io.nuite.modules.online_sales_app.entity.UserDeliveryInfo;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;
import io.nuite.modules.system.entity.online_sale.OnlineOrderEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OnlineOrderService {

    Integer submitOrder(List<Map<String, Integer>> stockChangeList, List<Integer> shoppingCartSeqList, OrderEntity orderEntity,
                        List<OrderProductsEntity> orderProductsList);

    void changeShoesDataStock(Integer shoesDataSeq, Integer changNum);

    List<OrderProductsEntity> getOrderProductsListByOrderSeq(Integer orderSeq);

    void cancelOrder(Integer orderSeq);

    void shipGotOrder(Integer orderSeq);

    void deleteOrder(Integer orderSeq);

    List<Map<String, Object>> getOrderListByUserSeq(Integer userSeq, Integer orderStatus, Integer start, Integer num);

    GoodsShoesEntity getGoodsShoesByShoesDateSeq(Integer shoesDataSeq);

    Map<String, Object> getOrderBySeq(Integer orderSeq, Integer userSeq);

    WxPayMpOrderResult createWxpayOrder(Integer orderSeq, CustomerUserInfo customerUserInfo, String customerIp);

    void wxPayNotify(String xmlResult) throws Exception;

    List<Map<String, Object>> getOrderProductsDetail(Integer orderSeq);

    UserDeliveryInfo getUserDeliveryInfoBySeq(Integer integer);

    String getCancelQRCode(Integer orderSeq, Integer userSeq, String openId);
    
    Integer orderSubmit(List<Integer> shoppingCartSeqList, OrderEntity OrderEntity,Date nowDate
			);
    
    List<OrderCartEntity> getOrderCartListByOrderSeq(Integer orderSeq);

    /**
     * 获取定货方订单列表
     * @param page
     * @param companySeq
     * @param year
     * @param periodSeq
     * @param customSeq
     * @return
     * @throws Exception
     */
    Page selectOrder(Page<OrderEntity> page,Integer companySeq, Integer year,Integer periodSeq,Integer customSeq) throws Exception;

    /**
     * 获取波次年份
     * @param brandSeq
     * @return
     * @throws Exception
     */
    List<Integer> selectYear(Integer brandSeq) throws Exception;

    /**
     * 获取波次
     * @param brandSeq
     * @param year
     * @return
     * @throws Exception
     */
    List<GoodsPeriodEntity> selectPeriod(Integer brandSeq, Integer year) throws Exception;

    /**
     * 获取定货方
     * @param companySeq
     * @param periodSeq
     * @return
     * @throws Exception
     */
    List<CustomerUserInfo> selectCustom(Integer companySeq,Integer periodSeq) throws Exception;
    List<Map<String, Object>> getOrderListByComapnySeq(List<Object> userSeqList, Integer orderStatus, Integer start, Integer num);

    OrderEntity getOrderEntityBySeq(Integer orderSeq);
    
    void update(OrderEntity orderEntity);

    /**
     * 获取定货方详情
     * @param companySeq
     * @param customSeq
     * @param periodSeq
     * @return
     * @throws Exception
     */
    List<OrderEntity> selectOrderDetail(Integer companySeq,Integer customSeq,Integer periodSeq) throws Exception;

    /**
     * 获取总订货量
     * @param companySeq
     * @param customSeq
     * @param periodSeq
     * @return
     * @throws Exception
     */
    Integer selectTotalOrderNum(Integer companySeq,Integer customSeq,Integer periodSeq) throws Exception;

    /**
     * 根据波次获取订单
     * @param companySeq
     * @param periodSeq
     * @return
     * @throws Exception
     */
    List<OrderEntity> selectOrderAllColumn(Integer companySeq,Integer periodSeq) throws Exception;
    
    List<OrderEntity> getOrderByUserSeq(Integer userSeq);
}
