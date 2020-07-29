package com.nuite.modules.erp.bserp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.util.List;

/**
 * 配货订单对应的配货单（用于发货）
 */
@Data
public class DingdanPh {
    @TableField(value = "DJBH")
    private String expressNum;

    @TableField(value = "LXDJ")
    private String orderNum;

    @TableField(value = "RQ")
    private String InputTime;

    @TableField(exist = false)
    private List<DingdanPhMX> mxList;
}
