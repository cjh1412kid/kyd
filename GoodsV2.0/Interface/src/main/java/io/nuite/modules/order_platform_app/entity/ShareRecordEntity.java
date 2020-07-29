package io.nuite.modules.order_platform_app.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 分享记录实体类
 * @author: jxj
 * @create: 2019-09-23 17:00
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM + "YHSR_OP_ShareRecord")
@Data
public class ShareRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号(主键)
     */
    @TableId
    @TableField(value = "Seq")
    private Integer seq;

    /**
     * 用户序号
     */
    @TableField(value = "User_Seq")
    private Integer userSeq;

    /**
     * 订货会货品序号
     */
    @TableField(value = "MeetingGoods_Seq")
    private Integer meetingGoodsSeq;

    /**
     * 插入时间
     */
    @TableField(value = "InputTime")
    private Date inputTime;
}
