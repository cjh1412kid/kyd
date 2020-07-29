package com.nuite.modules.erp.bserp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public abstract class JRD implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 单据编号
     */
    @TableField(value = "DJBH")
    private String orderNum;

    /**
     * 单据日期
     */
    @TableField(value = "RQ")
    private Date inputTime;

    /**
     * 外部单据号
     */
    @TableField(value = "YDJH")
    private String ydjh;

    /**
     * 作单标志
     */
    @TableField(value = "DJXZ")
    private String djxz;

    /**
     * 换货设定
     */
    @TableField(value = "FPLX")
    private String fplx;

    /**
     * 换货周期
     */
    @TableField(value = "DAYS")
    private String days;

    /**
     * 商店代码
     */
    @TableField(value = "DM1")
    private String accountName;

    /**
     * 商店库位
     */
    @TableField(value = "DM1_1")
    private String dm1_1;

    /**
     * 仓库
     */
    @TableField(value = "DM2")
    private String dm2;

    /**
     * 仓库库位
     */
    @TableField(value = "DM2_1")
    private String dm2_1;

    /**
     * 订单商品数量
     */
    @TableField(value = "SL")
    private Integer count;

    /**
     * 商品总价
     */
    @TableField(value = "JE")
    private Double orderPrice;

    /**
     * 可发货金额
     */
    @TableField(value = "BZJE")
    private Double paid;

    /**
     * 制单人，默认为系统管理员
     */
    @TableField(value = "ZDR")
    private String zdr;

    /**
     * 渠道代码
     */
    @TableField(value = "QDDM")
    private String qddm;

    /**
     * 员工代码，业务员,默认使用 人员待定
     */
    @TableField(value = "YGDM")
    private String ygdm;

    /**
     * 订单类型
     * 000 未定义
     * 001 首次预订单
     * 002 第一次补充订单
     * 003 补货单
     * 004 客户订单
     * 005 行部订
     * 006 第二次补充订单
     * 007 第三次补充订单
     * 008 其他省代订
     * 009 电商部订
     * 010 首单客户订
     */
    @TableField(value = "DM4")
    private String remark;

    /**
     * 价格选定
     */
    @TableField(value = "BYZD1")
    private String jgxd;

    /**
     * 折扣
     */
    @TableField(value = "BYZD12")
    private double zhekou;

    /**
     * 交货日期
     */
    @TableField(value = "YXRQ")
    private Date yxrq;

    /**
     * 制单日期
     */
    @TableField(value = "RQ_4")
    private Date zdrq;

    @TableField(exist = false)
    private Date storeTime;

    @TableField(exist = false)
    private int orderStatus;

    @TableField(exist = false)
    private List<DingdanMX> mxList;

    @TableField(exist = false)
    private List<DingdanPh> expressList;
}