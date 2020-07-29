package com.nuite.modules.erp.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/15 13:48
 * @Version: 1.0
 * @Description: 订单数据包装类，用于接收订单json数据
 */

@Data
public class OrderVo {

    /**
     * 订单编号
     */
    private String orderNum;

    /**
     * 订单总额
     */
    private Double amount;

    /**
     * 支付金额
     */
    private Double paid;

    private Date inputTime;

    /**
     * 订单明细
     */
    private List<OrderGoodsVo> orderGoods;

    /**
     * 订单商品总量
     */
    private Integer count;

    /**
     * 仓库id
     */
    private String userSeq;

    /**
     * 导入的订单类型0: 门店配货单(默认) 1:渠道调拨单 2:批发销货单
     */
    private int eRPOrderType;

}
