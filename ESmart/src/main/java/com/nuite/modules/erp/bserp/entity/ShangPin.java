package com.nuite.modules.erp.bserp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/8 16:59
 * @Version: 1.0
 * @Description:
 */

@Data
@TableName("SHANGPIN")
public class ShangPin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品编号
     */
    @TableField("SPDM")
    private String code;

    /**
     * 商品名称
     */
    @TableField("SPMC")
    private String name;

    /**
     * 标准售价
     */
    @TableField("BZSJ")
    private BigDecimal salePrice;

    /**
     * 售价1
     */
    @TableField("SJ1")
    private BigDecimal sj1;

    /**
     * 售价2：批发价
     */
    @TableField("SJ2")
    private BigDecimal sj2;

    /**
     * 售价4：批发价
     */
    @TableField("SJ4")
    private BigDecimal sj4;

    /**
     * 大类
     */
    @TableField("BYZD4")
    private String categoryCode;
    /**
     * 季节
     */
    @TableField("BYZD5")
    private String seasonCode;
    /**
     * 季节
     */
    @TableField(exist = false)
    private String seasonName;
    /**
     * 年份
     */
    @TableField("BYZD8")
    private Integer year;
    /**
     * 附加属性1
     */
    @TableField("FJSX1")
    private String sx1;
    /**
     * 附加属性2
     */
    @TableField("FJSX2")
    private String sx2;
    /**
     * 附加属性3
     */
    @TableField("FJSX3")
    private String sx3;
    /**
     * 附加属性4
     */
    @TableField("FJSX4")
    private String sx4;
    /**
     * 附加属性5
     */
    @TableField("FJSX5")
    private String sx5;
    /**
     * 附加属性6
     */
    @TableField("FJSX6")
    private String sx6;
    /**
     * 附加属性7
     */
    @TableField("FJSX7")
    private String sx7;
    /**
     * 附加属性8
     */
    @TableField("FJSX8")
    private String sx8;
    /**
     * 附加属性9
     */
    @TableField("FJSX9")
    private String sx9;
    /**
     * 附加属性10
     */
    @TableField("FJSX10")
    private String sx10;
    /**
     * 附加属性11
     */
    @TableField("FJSX11")
    private String sx11;
    /**
     * 附加属性12
     */
    @TableField("FJSX12")
    private String sx12;
    /**
     * 附加属性13
     */
    @TableField("FJSX13")
    private String sx13;
    /**
     * 附加属性14
     */
    @TableField("FJSX14")
    private String sx14;
    /**
     * 附加属性15
     */
    @TableField("FJSX15")
    private String sx15;
    /**
     * 附加属性16
     */
    @TableField("FJSX16")
    private String sx16;

    @TableField(exist = false)
    private List<GoodsStock> stocks;

}
