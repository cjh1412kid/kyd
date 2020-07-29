package io.nuite.modules.online_sales_app.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(DatabaseNames.SR_ONLINE_SALES + "YHSR_OLS_MiniApp")
public class MiniAppEntity implements Serializable {

    @TableId(value = "Seq")
    private Integer seq;

    @TableField(value = "Company_Seq")
    private Integer companySeq;

    @TableField(value = "Brand_Seq")
    private Integer brandSeq;

    @TableField(value = "AppId")
    private String appId;

    @TableField(value = "AppSecret")
    private String appSecret;

    @TableField(value = "MchId")
    private String mchId;

    @TableField(value = "MchKey")
    private String mchKey;

    @TableField(value = "KeyPath")
    private String keyPath;

   
}
