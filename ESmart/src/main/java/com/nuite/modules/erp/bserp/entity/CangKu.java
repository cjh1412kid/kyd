package com.nuite.modules.erp.bserp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("CANGKU")
public class CangKu implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 账号
     */
    @TableField(value = "CKDM")
    private String accountName;

    /**
     * 姓名
     */
    @TableField(value = "CKMC")
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

    // --自定义字段-- //

}
