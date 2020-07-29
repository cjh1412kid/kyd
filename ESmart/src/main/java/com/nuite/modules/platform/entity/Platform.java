package com.nuite.modules.platform.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
@TableName("platform")
public class Platform implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号(主键)
     */
    @TableId(value = "Seq")
    private Integer seq;

    /**
     * 平台的调用key
     */
    @TableField(value = "PlatKey")
    private String platKey;

    /**
     * 平台的调用secret
     */
    @TableField(value = "PlatSecret")
    private String platSecret;

    /**
     * erp的使用品牌
     */
    @TableField(value = "ErpBrand")
    private String erpBrand;

    /**
     * 传统erp，电商erp
     */
    @TableField(value = "ErpType")
    private String erpType;

    /**
     * erp的对应版本
     */
    @TableField(value = "ErpVersion")
    private String erpVersion;

    /**
     * erp的使用公司名称
     */
    @TableField(value = "Company")
    private String company;

    /**
     * platform创建是时间
     */
    @TableField(value = "InputTime")
    private String inputTime;

    /**
     * service的bean名称
     */
    @TableField(value = "BeanName")
    private String beanName;

    /**
     * platform是否删除
     */
    @TableLogic
    @TableField(value = "Del")
    private String del;
}
