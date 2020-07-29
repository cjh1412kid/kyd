package com.nuite.modules.erp.entity.yiting.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("KEHU")
public class YTKeHu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 厂家标记
     */
    @TableField(exist = false)
    private String companyFalg = "yiting";
    /**
     * 账号
     */
    @TableField(value = "KHDM")
    private String accountName;
    /**
     * 性质代码
     * ‘0’－代理客户
     * ‘1’－加盟店（备用）
     * ‘2’－直营店
     * ‘3’－分公司
     * ‘4’－物料客户
     */
    @TableField(value = "XZDM")
    private String userType;

    /**
     * 姓名
     */
    @TableField(value = "KHMC")
    private String userName;
    /**
     * 手机号
     */
    @TableField(value = "SJ")
    private String telephone;
    /**
     * 电话
     */
    @TableField(value = "DH1")
    private String tel;

    @TableField(value = "DZ")
    private String address;

    @TableField("LXR")
    private String linkman;

    @TableField("QYDM")
    private String quyuCode;

    @TableField(exist = false)
    private String quyuName;

    @TableField(value = "QDDM")
    private String qudaoCode;

    @TableField(exist = false)
    private String qudaoName;

    /**
     * 账号所属类型：1.工厂，2.分公司，3.代理商，4.工厂子账号
     */
    @TableField(exist = false)
    private Integer attachType;

    /**
     * 账号销售类型：1.工厂，2.贴牌商，3.批发商，4.直营店
     */
    @TableField(exist = false)
    private Integer saleType;
}
