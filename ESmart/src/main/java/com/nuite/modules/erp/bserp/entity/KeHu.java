package com.nuite.modules.erp.bserp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("KEHU")
public class KeHu implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 账号
     */
    @TableId(value = "KHDM")
    private String accountName;
    /**
     * 性质代码
     * ‘0’－代理客户
     ‘1’－加盟店（备用）
     ‘2’－直营店
     ‘3’－分公司
     ‘4’－物料客户
     */
    @TableField(value = "XZDM")
    private String type;

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
    private String  linkman;

    @TableField("QYDM")
    private String quyuCode;

    @TableField(exist = false)
    private String quyuName;

    @TableField(value = "QDDM")
    private String qudaoCode;

    @TableField(exist = false)
    private String qudaoName;
}
