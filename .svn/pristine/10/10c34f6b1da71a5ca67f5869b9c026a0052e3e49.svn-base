package com.nuite.modules.erp.bserp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class JRDMX implements Serializable {
    /**
     * 单据编号
     */
    @TableField(value = "DJBH")
    private String orderNum;

    /**
     * 内容编号
     */
    @TableField(value = "MIBH")
    private String nbBH;

    /**
     * 明细编号
     */
    @TableField(value = "MXBH")
    private String seq;

    /**
     * 商品代码
     */
    @TableField(value = "SPDM")
    private String goodsSeq;

    /**
     * 颜色代码
     */
    @TableField(value = "GG1DM")
    private String colorSeq;

    /**
     * 尺码代码
     */
    @TableField(value = "GG2DM")
    private String sizeSeq;

    /**
     * 数量
     */
    @TableField(value = "SL")
    private int buyCount;

    /**
     * 已发货数量
     */
    @TableField(value = "SL_2")
    private int deliverNum;

    /**
     * 单价
     */
    @TableField(value = "DJ")
    private Double productPrice;

    /**
     * 商品总价
     */
    @TableField(value = "JE")
    private Double amount;

    /**
     * 总价
     */
    @TableField(value = "BZJE")
    private Double bzje;

    /**
     * 参考价
     */
    @TableField(value = "CKJ")
    private Double ckj;
}