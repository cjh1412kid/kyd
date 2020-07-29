package io.nuite.modules.online_sales_app.entity;

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
 * @author admin
 * @email
 * @date 2018-04-11 11:29:57
 */
@Data
@TableName(DatabaseNames.SR_ONLINE_SALES + "YHSR_OLS_Order")
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号(主键)
     */
    @TableId
    @TableField(value = "Seq")
    private Integer seq;
    /**
     * 客户Seq(外键:userInfo表)
     */
    @TableField(value = "User_Seq")
    private Integer userSeq;
    /**
     * 订单编号
     */
    @TableField(value = "OrderNum")
    private String orderNum;
    /**
     * 订单价格
     */
    @TableField(value = "OrderPrice")
    private BigDecimal orderPrice;
    /**
     * 已付金额
     */
    @TableField(value = "Paid")
    private BigDecimal paid;
    /**
     * 订单状态
     * 工厂方：0待支付、1未发货、2已发货、3已到货、4已取消
     * 消费者方：0待支付、1待发货、2已发货、3已收货、4已取消
     */
    @TableField(value = "OrderStatus")
    private Integer orderStatus;
    /**
     * 商户序号
     */
    @TableField(value = "Company_Seq")
    private Integer companySeq;
    /**
     * 门店序号
     */
    @TableField(value = "Shop_Seq")
    private Integer shopSeq;
    /**
     * 快递公司序号
     */
    @TableField(value = "ExpressCompany_Seq")
    private Integer expressCompanySeq;
    /**
     * 快递单号
     */
    @TableField(value = "ExpressNo")
    private String expressNo;
    /**
     * 收货人姓名
     */
    @TableField(value = "ReceiverName")
    private String receiverName;
    /**
     * 收货人电话
     */
    @TableField(value = "Telephone")
    private String telephone;
    /**
     * 收货人地址
     */
    @TableField(value = "FullAddress")
    private String fullAddress;
    /**
     * 插入时间
     */
    @TableField(value = "InputTime")
    private Date inputTime;
    /**
     * 付款时间
     */
    @TableField(value = "PaymentTime")
    private Date paymentTime;
    /**
     * 发货时间
     */
    @TableField(value = "DeliverTime")
    private Date deliverTime;
    /**
     * 收货时间
     */
    @TableField(value = "ReceiveTime")
    private Date receiveTime;
    /**
     * 备注
     */
    @TableField(value = "Remark")
    private String remark;
    /**
     * 留言
     */
    @TableField(value = "Suggestion")
    private String suggestion;
    /**
     * 删除标识(0:未删除,1:已删除)
     */
    @TableLogic
    @TableField(value = "Del")
    private Integer del;

    @TableField(value = "WxpayPrepayId")
    private String wxpayPrepayId;

    @TableField(value = "WxpayPrepayTime")
    private Date wxpayPrepayTime;

    @TableField(value = "WxTransactionId")
    private String wxTransactionId;

    /**
     * 门店自提
     */
    @TableField(value = "SelfPick")
    private Integer selfPick;

    @TableField(value = "ExpectedTime")
    private Date expectedTime;
    
    @TableField(value = "IsOem")
    private Integer isOem;
    
    @TableField(value = "OemUrl")
    private String oemUrl;

    /**
     * 定货方姓名
     */
    @TableField(exist = false)
    private String customUser;

    /**
     * 订货总量
     */
    @TableField(exist = false)
    private Integer totalSelectNum;

    /**
     * 预付款
     */
    @TableField(exist = false)
    private BigDecimal mustPay;

    /**
     * 是否已付
     */
    @TableField(exist = false)
    private Boolean isPaid;

    /**
     * 波次名称
     */
    @TableField(exist = false)
    private String periodName;

    /**
     * 快递名称
     */
    @TableField(exist = false)
    private String expressName;

    /**
     * 配件数
     */
    @TableField(exist = false)
    private Integer perBoxNum;
}
