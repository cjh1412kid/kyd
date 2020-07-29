package io.nuite.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 配码模版
 *
 * @author yangchuang
 * @date 2019-04-26 10:18:29
 */

@Data
@TableName(DatabaseNames.SR_ORDER_PLATFORM + "YHSR_OP_SizeAllotTemplate")
public class SizeAllotTemplateEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 配码模版序号
     */
    @TableId(value = "Seq")
    private Integer seq;
    /**
     * 公司序号
     */
    @TableField(value = "Company_Seq")
    private Integer companySeq;
    /**
     * 模板名称
     */
    @TableField(value = "Name")
    private String name;
    /**
     * 最小尺码
     */
    @TableField(value = "MinSize")
    private Integer minSize;
    /**
     * 最大尺码
     */
    @TableField(value = "MaxSize")
    private Integer maxSize;
    /**
     * 插入时间
     */
    @TableField(value = "InputTime")
    private Date inputTime;
    
    /**
     * 模版细节
     */
    @TableField(exist = false)
    private List<SizeAllotTemplateDetailEntity> details;
    
}
