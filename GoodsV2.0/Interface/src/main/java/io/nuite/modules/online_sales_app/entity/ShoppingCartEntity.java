package io.nuite.modules.online_sales_app.entity;

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
 * @date 2018-04-11 11:29:57
 */
@TableName(DatabaseNames.SR_ONLINE_SALES+"YHSR_OLS_ShoppingCart")
public class ShoppingCartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号(主键)
	 */
	@TableId
	@TableField(value = "Seq")
	private Integer seq;
	/**
	 * 用户Seq(外键:YHSR_Base_User表)
	 */
	@TableField(value = "User_Seq")
	private Integer userSeq;
	/**
	 * 鞋子数据序号(外键:YHSR_OP_ShoesData表)
	 */
	@TableField(value = "ShoesData_Seq")
	private Integer shoesDataSeq;
	/**
	 * 商品分享链
	 */
	@TableField(value = "OpenIDLinks")
	private String openIDLinks;
	/**
	 * 商户序号
	 */
	@TableField(value = "Company_Seq")
	private Integer companySeq;
	/**
	 * 门店序号
	 */
	@TableField(value = "Shop_Seq")
	private Integer shopSeq;
	/**
	 * 数量
	 */
	@TableField(value = "BuyCount")
	private Integer buyCount;
	/**
	 * 总价格
	 */
	@TableField(value = "TotalPrice")
	private BigDecimal totalPrice;
	/**
	 * 是否勾选 0不勾选 1勾选
	 */
	@TableField(value = "IsChecked")
	private Integer isChecked;
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
	 * 设置：用户Seq(外键:YHSR_Base_User表)
	 */
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	/**
	 * 获取：用户Seq(外键:YHSR_Base_User表)
	 */
	public Integer getUserSeq() {
		return userSeq;
	}
	/**
	 * 设置：鞋子数据序号(外键:YHSR_OP_ShoesData表)
	 */
	public void setShoesDataSeq(Integer shoesDataSeq) {
		this.shoesDataSeq = shoesDataSeq;
	}
	/**
	 * 获取：鞋子数据序号(外键:YHSR_OP_ShoesData表)
	 */
	public Integer getShoesDataSeq() {
		return shoesDataSeq;
	}
	/**
	 * 设置：数量
	 */
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
	/**
	 * 获取：数量
	 */
	public Integer getBuyCount() {
		return buyCount;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}
	/**
	 * 设置：插入时间
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	/**
	 * 获取：插入时间
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
	public String getOpenIDLinks() {
		return openIDLinks;
	}
	public void setOpenIDLinks(String openIDLinks) {
		this.openIDLinks = openIDLinks;
	}
	public Integer getCompanySeq() {
		return companySeq;
	}
	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}
	public Integer getShopSeq() {
		return shopSeq;
	}
	public void setShopSeq(Integer shopSeq) {
		this.shopSeq = shopSeq;
	}
	
}
