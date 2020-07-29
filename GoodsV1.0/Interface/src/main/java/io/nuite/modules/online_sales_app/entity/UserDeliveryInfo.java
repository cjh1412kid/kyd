package io.nuite.modules.online_sales_app.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(DatabaseNames.SR_ONLINE_SALES + "YHSR_OLS_UserDeliveryInfo")
public class UserDeliveryInfo implements Serializable {
    @TableId(value = "Seq")
    private Long seq;

    @TableField(value = "User_Seq")
    private Integer customSeq;

    @TableField(value = "RecipientsName")
    private String recipientsName;

    @TableField(value = "Address")
    private String address;

    @TableField(value = "Telephone")
    private String telephone;

    @TableField(value = "Isdefault")
    private boolean isDefault;

    @TableField(value = "InputTime")
    private Date inputTime;

    @TableField(value = "Del")
    private int del;

    
}
