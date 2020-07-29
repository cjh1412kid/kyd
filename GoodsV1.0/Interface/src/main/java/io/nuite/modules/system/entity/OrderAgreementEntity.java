package io.nuite.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同模板
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-04-20 16:05:38
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_OrderAgreement")
public class OrderAgreementEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * 合同名称
	 */
	@TableField(value = "Name")
	private String name;
	/**
	 * 合同内容
	 */
	@TableField(value = "AgreementContent")
	private String agreementContent;
	/**
	 * $column.comments
	 */
	@TableField(value = "Company_Seq")
	private Integer companySeq;
	/**
	 * $column.comments
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * $column.comments
	 */
	@TableLogic
	@TableField(value = "Del")
	private Integer del;

	/**
	 * 设置：${column.comments}
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getSeq() {
		return seq;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAgreementContent(String agreementContent) {
		this.agreementContent = agreementContent;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getAgreementContent() {
		return agreementContent;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getCompanySeq() {
		return companySeq;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Date getInputTime() {
		return inputTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setDel(Integer del) {
		this.del = del;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getDel() {
		return del;
	}
}
