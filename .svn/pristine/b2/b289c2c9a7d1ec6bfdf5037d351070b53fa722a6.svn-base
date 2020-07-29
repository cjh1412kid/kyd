package io.nuite.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订货会订单货品
 *
 * @author yangchuang
 * @date 2019-04-17 13:42:11
 */

@Data
@TableName(DatabaseNames.SR_ORDER_PLATFORM + "YHSR_OP_MeetingOrderProduct")
public class MeetingOrderProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 订货会订单商品序号
     */
    @TableId(value = "Seq")
    private Integer seq;
    /**
     * 订货会订单序号
     */
    @TableField(value = "MeetingOrder_Seq")
    private Integer meetingOrderSeq;
    /**
     * 订货会商品序号
     */
    @TableField(value = "MeetingGoods_Seq")
    private Integer meetingGoodsSeq;
    /**
     * 颜色序号
     */
    @TableField(value = "Color_Seq")
    private Integer colorSeq;
    /**
     * 尺码
     */
    @TableField(value = "Size")
    private Integer size;
    /**
     * 购买数量
     */
    @TableField(value = "BuyCount")
    private Integer buyCount;
    /**
     * 创建时间
     */
    @TableField(value = "InputTime")
    private Date inputTime;
    /**
     * 是否删除（1删除 0未删除）
     */
    @TableLogic
    @TableField(value = "Del")
    private Integer del;
    /**
     * 是否取消（1取消 0未取消）
     */
    @TableField(value = "Cancel")
    private Integer cancel;
    
    /**
     * 颜色
     */
    @TableField(exist = false)
    private String colorName;
    
    /**
     * 货号
     */
    @TableField(exist = false)
    private String goodID;
    /**
     * 货号图片
     */
    @TableField(exist = false)
    private String picName;
    /**
     * 最小尺码
     */
    @TableField(exist = false)
    private Integer minSize;
    /**
     * 最大尺码
     */
    @TableField(exist = false)
    private Integer maxSize;
    /**
     * 区域
     */
    @TableField(exist = false)
    private String areaName;

    /**
     * 订货方
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 供应价
     */
    @TableField(exist = false)
    private BigDecimal price;
}
