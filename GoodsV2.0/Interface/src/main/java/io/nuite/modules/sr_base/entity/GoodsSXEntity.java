package io.nuite.modules.sr_base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 0
 *
 * @author admin
 * @email
 * @date 2018-04-11 11:38:09
 */
@TableName(DatabaseNames.SR_BASE + "YHSR_Goods_SX")
public class GoodsSXEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号(主键)
     */
    @TableId
    @TableField(value = "Seq")
    private Integer seq;
    /**
     * 公司序号(外键:YHSR_Base_Company表)
     */
    @TableField(value = "Company_Seq")
    private Integer companySeq;
    /**
     * 属性对应Goods表字段（如SX1,SX2...）
     */
    @TableField(value = "SXID")
    private String SXId;
    /**
     * 属性中文含义
     */
    @TableField(value = "SXName")
    private String SXName;
    /**
     * 创建时间
     */
    @TableField(value = "InputTime")
    private Date inputTime;
    /**
     * 是否可见（0:可见 1:不可见）
     */
    @TableField(value = "Visible")
    private Integer visible;
    /**
     * 删除标识(0:未删除,1:已删除)
     */
    @TableLogic
    @TableField(value = "Del")
    private Integer del;

    @TableField(exist = false)
    private List<GoodsSXOptionEntity> optionsList;


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

    public String getSXId() {
        return SXId;
    }

    public void setSXId(String sXId) {
        SXId = sXId;
    }

    public String getSXName() {
        return SXName;
    }

    public void setSXName(String sXName) {
        SXName = sXName;
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

    public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
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

    public List<GoodsSXOptionEntity> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<GoodsSXOptionEntity> optionsList) {
        this.optionsList = optionsList;
    }
}
