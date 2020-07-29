package com.nuite.modules.erp.bserp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

/**
 * 配货单明细
 */
@Data
public class DingdanPhMX {
    @TableField(value = "DJBH")
    private String expressNum;

    @TableField(value = "SPDM")
    private String goodsNum;

    @TableField("GG1DM")
    private String colorNum;

    @TableField("GG2DM")
    private String sizeNum;

    @TableField("SL")
    private String num;
}