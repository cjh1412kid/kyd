package io.nuite.modules.system.entity.online_sale;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @email
 * @date 2018-04-11 11:29:57
 */
@TableName(DatabaseNames.SR_ONLINE_SALES + "YHSR_OLS_Order")
public class OnlineOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号(主键)
	 */
	@TableId
	@TableField(value = "Seq")
	private Integer seq;
	/**
	 * 客户Seq(外键:userInfo表)
	 */
	@TableField(value = "User_Seq")
	private Integer userSeq;

	/**
	 * 订单编号
	 */
	@TableField(value = "OrderNum")
	private String orderNum;
	/**
	 * 订单价格
	 */
	@TableField(value = "OrderPrice")
	private BigDecimal orderPrice;

	/**
	 * 已付金额
	 */
	@TableField(value = "Paid")
	private BigDecimal paid;

	/**
	 * 订单状态 工厂方：0待确认、1待支付、2未发货、3已发货、4已到货、5已取消 订货方：0待确认、1待支付、2待发货、3已发货、4已收货、5已取消
	 */
	@TableField(value = "OrderStatus")
	private Integer orderStatus;
	/**
	 * 商户序号
	 */
	@TableField(value = "Company_Seq")
	private Integer companySeq;
	/**
	 * 快递公司序号
	 */
	@TableField(value = "ExpressCompany_Seq")
	private Integer expressCompanySeq;
	/**
	 * 快递单号
	 */
	@TableField(value = "ExpressNo")
	private String expressNo;
	/**
	 * 插入时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;

	/**
	 * 付款时间
	 */
	@TableField(value = "PaymentTime")
	private Date paymentTime;
	/**
	 * 发货时间
	 */
	@TableField(value = "DeliverTime")
	private Date deliverTime;
	/**
	 * 收货时间
	 */
	@TableField(value = "ReceiveTime")
	private Date receiveTime;
	/**
	 * 备注
	 */
	@TableField(value = "Remark")
	private String remark;
	/**
	 * 留言
	 */
	@TableField(value = "Suggestion")
	private String suggestion;
	/**
	 * 删除标识(0:未删除,1:已删除)
	 */
	@TableLogic
	@TableField(value = "Del")
	private Integer del;



	// --自定义参数-- //
	/**
	 * 快递公司名称
	 */
	@TableField(exist = false)
	private String expressName;
	/**
	 * 快递公司代码
	 */
	@TableField(exist = false)
	private String expressCode;
	/**
	 * 订单状态
	 */
	@TableField(exist = false)
	private String statusName;
	/**
	 * 一个订单种对应的种类数
	 */
	@TableField(exist = false)
	private Integer species;
	/**
	 * 该订单中所有鞋子的总数量
	 */
	@TableField(exist = false)
	private Float total;
	/**
	 * 该订单中所有鞋子对应的图片
	 */
	@TableField(exist = false)
	private List<String> photo;
	/**
	 * 收货地址
	 */
	@TableField(exist = false)
	private String address;

	/**
	 * 买家名称
	 */
	@TableField(exist = false)
	private String orderingparty;

	
	
	
	public String getOrderingparty() {
		return orderingparty;
	}

	public void setOrderingparty(String orderingparty) {
		this.orderingparty = orderingparty;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public BigDecimal getPaid() {
		return paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}

	public Integer getExpressCompanySeq() {
		return expressCompanySeq;
	}

	public void setExpressCompanySeq(Integer expressCompanySeq) {
		this.expressCompanySeq = expressCompanySeq;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getSpecies() {
		return species;
	}

	public void setSpecies(Integer species) {
		this.species = species;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public List<String> getPhoto() {
		return photo;
	}

	public void setPhoto(List<String> photo) {
		this.photo = photo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
