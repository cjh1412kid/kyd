package io.nuite.modules.sr_base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 公司类型实体类
 * @author: jxj
 * @create: 2019-09-23 14:43
 */
@TableName(DatabaseNames.SR_BASE+"YHSR_Base_CompanyType")
@Data
public class BaseCompanyTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号(主键)
     */
    @TableId
    @TableField(value = "Seq")
    private Integer seq;

    /**
     * 类型名称
     */
    @TableField(value = "TypeName")
    private String typeName;

    /**
     * 删除标记
     */
    @TableLogic
    @TableField(value = "Del")
    private Integer del;
}
