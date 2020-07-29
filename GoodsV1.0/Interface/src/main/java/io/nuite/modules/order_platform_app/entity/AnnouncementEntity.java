package io.nuite.modules.order_platform_app.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.util.Date;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:29:57
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_Announcement")
public class AnnouncementEntity implements Serializable {
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
	 * 公告类型0：其他，1：新品，2：直播
	 */
	@TableField(value = "Type")
	private Integer type;
	/**
	 * 内容
	 */
	@TableField(value = "Content")
	private String content;
	/**
	 * 过期时间
	 */
	@TableField(value = "ExpirationTime")
	private Date expirationTime;
	/**
	 * 插入时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * 删除标识(0:未删除,1:已删除)
	 */
	@TableLogic
	@TableField(value = "Del")
	private Integer del;
	
	
	
	
	//自定义字段
	/**
	 * 公告类型名称
	 */
	@TableField(exist = false)
	private String typeName;
	
	
	
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getCompanySeq() {
		return companySeq;
	}
	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
    @Override
    public String toString() {
        return "AnnouncementEntity [seq=" + seq + ", companySeq=" + companySeq + ", type=" + type + ", content="
                + content + ", expirationTime=" + expirationTime + ", inputTime=" + inputTime + ", del=" + del
                + ", typeName=" + typeName + "]";
    }

}
