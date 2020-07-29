package io.nuite.modules.online_sales_app.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(DatabaseNames.SR_ONLINE_SALES + "YHSR_OLS_CustomUserInfo")
public class CustomerUserInfo implements Serializable {

    @TableId(value = "Seq")
    private Integer seq;

    //获取佣金权限
    @TableField(value = "IsCommission")
    private int isCommission;

    @TableField(value = "RealName")
    private String realName;

    @TableField(value = "Telephone")
    private String telephone;

    @TableField(value = "BankCardNO")
    private String bankCardNo;

    //标识是企业员工还是注册会员0商户员工，1注册会员
    @TableField(value = "Flag")
    private int flag;

    @TableField(value = "Isuse")
    private Integer isUse;

    @TableField(value = "Remark")
    private String remark;

    @TableField(value = "Openid")
    private String openId;

    @TableField(value = "Sessionkey")
    private String sessionKey;

    @TableField(value = "Unionid")
    private String unionId;

    @TableField(value = "Nickname")
    private String nickName;

    //性别1男性，2是女性，0未知
    @TableField(value = "Sex")
    private int sex;

    @TableField(value = "Country")
    private String country;

    @TableField(value = "Province")
    private String province;

    @TableField(value = "City")
    private String city;

    @TableField(value = "InputTime")
    private Date inputTime;

    @TableField(value = "Del")
    private int del;

    @TableField(value = "Company_Seq")
    private Integer companySeq;

    @TableField(value = "Brand_Seq")
    private Integer brandSeq;

    @TableField(value = "Shop_Seq")
    private Integer shopSeq;

 

    
}
