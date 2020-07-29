package io.nuite.modules.order_platform_app.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:29:58
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingPlan")
public class MeetingPlanEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 序号(主键)
	 */
	@TableId
	@TableField(value = "Seq")
	private Integer seq;
	/**
	 * 公司序号
	 */
	@TableField(value = "Company_Seq")
	private Integer companySeq;
	/**
	 * 波次序号
	 */
	@TableField(value = "Period_Seq")
	private Integer periodSeq;
	/**
	 * 用户序号
	 */
	@TableField(value = "User_Seq")
	private Integer userSeq;
	/**
	 * 分类序号(大类)
	 */
	@TableField(value = "Category_Seq")
	private Integer categorySeq;
    /**
     * 品类
     */
    @TableField(value = "SX2")
    private String SX2;
    /**
     * 风格
     */
    @TableField(value = "SX3")
    private String SX3;
	/**
	 * 计划订货数量
	 */
	@TableField(value = "PlanNum")
	private Integer planNum;
	/**
	 * 计划订货金额
	 */
	@TableField(value = "PlanMoney")
	private BigDecimal planMoney;
	/**
	 * 计划订货款数(货号数)
	 */
	@TableField(value = "PlanGoodsIDs")
	private Integer planGoodsIDs;
	/**
	 * 实际订货数量
	 */
	@TableField(value = "ActualNum")
	private Integer actualNum;
	/**
	 * 实际订货金额
	 */
	@TableField(value = "ActualMoney")
	private BigDecimal actualMoney;
	/**
	 * 实际订货款数(货号数)
	 */
	@TableField(value = "ActualGoodsIDs")
	private Integer actualGoodsIDs;
	/**
	 * 已订货的货号
	 */
	@TableField(value = "ActualGoodsIDSArr")
	private String actualGoodsIDSArr;
	/**
	 * 计划上传时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * 修改时间
	 */
	@TableField(value = "UpdateTime")
	private Date updateTime;
	/**
	 * 删除标识(0:未删除,1:已删除)
	 */
	@TableLogic
	@TableField(value = "Del")
	private Integer del;
	
	
	
	
	
	
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
	public Integer getPeriodSeq() {
		return periodSeq;
	}
	public void setPeriodSeq(Integer periodSeq) {
		this.periodSeq = periodSeq;
	}
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	public Integer getCategorySeq() {
		return categorySeq;
	}
	public void setCategorySeq(Integer categorySeq) {
		this.categorySeq = categorySeq;
	}
	public String getSX2() {
		return SX2;
	}
	public void setSX2(String sX2) {
		SX2 = sX2;
	}
	public String getSX3() {
		return SX3;
	}
	public void setSX3(String sX3) {
		SX3 = sX3;
	}
	public Integer getPlanNum() {
		return planNum;
	}
	public void setPlanNum(Integer planNum) {
		this.planNum = planNum;
	}
	public BigDecimal getPlanMoney() {
		return planMoney;
	}
	public void setPlanMoney(BigDecimal planMoney) {
		this.planMoney = planMoney;
	}
	public Integer getPlanGoodsIDs() {
		return planGoodsIDs;
	}
	public void setPlanGoodsIDs(Integer planGoodsIDs) {
		this.planGoodsIDs = planGoodsIDs;
	}
	public Integer getActualNum() {
		return actualNum;
	}
	public void setActualNum(Integer actualNum) {
		this.actualNum = actualNum;
	}
	public BigDecimal getActualMoney() {
		return actualMoney;
	}
	public void setActualMoney(BigDecimal actualMoney) {
		this.actualMoney = actualMoney;
	}
	public Integer getActualGoodsIDs() {
		return actualGoodsIDs;
	}
	public void setActualGoodsIDs(Integer actualGoodsIDs) {
		this.actualGoodsIDs = actualGoodsIDs;
	}
	public String getActualGoodsIDSArr() {
		return actualGoodsIDSArr;
	}
	public void setActualGoodsIDSArr(String actualGoodsIDSArr) {
		this.actualGoodsIDSArr = actualGoodsIDSArr;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	
}
