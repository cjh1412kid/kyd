package io.nuite.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;
import lombok.Data;

import java.io.Serializable;

/**
 * 配码模版细节
 *
 * @author yangchuang
 * @date 2019-04-26 10:18:29
 */

@Data
@TableName(DatabaseNames.SR_ORDER_PLATFORM + "YHSR_OP_SizeAllotTemplateDetail")
public class SizeAllotTemplateDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 配码模版细节序号
     */
    @TableId(value = "Seq")
    private Integer seq;
    /**
     * 模版序号
     */
    @TableField(value = "TemplateSeq")
    private Integer templateSeq;
    /**
     * 尺码
     */
    @TableField(value = "Size")
    private Integer size;
    /**
     * 尺码数量
     */
    @TableField(value = "Per")
    private Integer per;
    
}
