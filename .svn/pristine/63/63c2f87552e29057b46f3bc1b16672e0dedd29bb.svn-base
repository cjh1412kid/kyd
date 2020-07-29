package io.nuite.modules.sr_base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: yangchuang
 * @Date: 2018-7-17
 * @Version: 1.0
 * @Description:
 */

@TableName(DatabaseNames.SR_ORDER_PLATFORM + "YHSR_Ueditor_Record")
public class UeditorRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号(主键)
     */
    @TableId
    @TableField(value = "Seq")
    private Integer seq;

/*	@Transient
	private Integer parentSeq;*/
    /**
     * 公司序号(外键:YHSR_Base_Company表)
     */
    @TableField(value = "Company_Seq")
    private Integer companySeq;
    /**
     * 分类名称
     */
    @TableField(value = "Name")
    private String name;
    /**
     * 创建时间
     */
    @TableField(value = "InputTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inputTime;

    /**
     * 是否正在被用（0:未使用 1:正在被用）
     */
    @TableField(value = "Used")
    private Integer used;

    /**
     * 删除标识(0:未删除,1:已删除)
     */
//	@TableLogic
    @TableField(value = "Del")
    private Integer del;

    @TableField(value = "Content")
    private String content;

    /**
     * 设置：序号(主键)
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * 获取：序号(主键)
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置：公司序号(外键:YHSR_Base_Company表)
     */
    public void setCompanySeq(Integer companySeq) {
        this.companySeq = companySeq;
    }

    /**
     * 获取：公司序号(外键:YHSR_Base_Company表)
     */
    public Integer getCompanySeq() {
        return companySeq;
    }

    /**
     * 设置：分类名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：分类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：创建时间
     */
    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getInputTime() {
        return inputTime;
    }

    /**
     * 设置：删除标识(0:未删除,1:已删除)
     */
    public void setDel(Integer del) {
        this.del = del;
    }

    /**
     * 获取：删除标识(0:未删除,1:已删除)
     */
    public Integer getDel() {
        return del;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UeditorRecordEntity{");
        sb.append("seq=").append(seq);
        sb.append(", companySeq=").append(companySeq);
        sb.append(", name='").append(name).append('\'');
        sb.append(", inputTime=").append(inputTime);
        sb.append(", used=").append(used);
        sb.append(", del=").append(del);
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
