package com.nuite.modules.erp.bserp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品库存实体类
 */

@Data
@TableName("SPKCB")
public class GoodsStock implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableField("SPDM")
    private String goodsCode;

    /**
     * 颜色id
     */
    @TableField("GG1DM")
    private String colorCode;

    @TableField(exist = false)
    private String colorName;

    /**
     * 尺码id
     */
    @TableField("GG2DM")
    private String sizeCode;

    @TableField(exist = false)
    private String sizeName;

    /**
     * 库存 在库数量
     */
    @TableField("SL")
    private int count;

    /**
     * 库存 通知数量（下单）
     */
    @TableField("SL1")
    private int count1;

    /**
     * 库存 在途数量（发货）
     */
    @TableField("SL2")
    private int count2;

    /**
     * 仓库代码
     */
    @TableField("CKDM")
    private String CKCode;
    /**
     * 库位代码
     */
    @TableField("KWDM")
    private String KWCode;

}
