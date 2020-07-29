package io.nuite.modules.online_sales_app.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;
import lombok.Data;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-06-25 09:20:40
 */
@Data
@TableName(DatabaseNames.SR_ONLINE_SALES +"YHSR_OLS_SalesShoppingCartDetail")
public class SalesShoppingCartDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号(主键)
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * 购物车序号
	 */
	@TableField(value = "SalesShoppingCart_Seq")
	private Integer salesShoppingCartSeq;
	/**
	 * 订货会鞋子序号
	 */
	@TableField(value = "ShoeData_Seq")
	private Integer shoeDataSeq;
	/**
	 * 选款数量
	 */
	@TableField(value = "SelectNum")
	private Integer selectNum;
	/**
	 * 插入时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * 购物车配箱序号
	 */
	@TableField(value = "SalesShoppingCartDistributeBox_Seq")
	private Integer salesShoppingCartDistributeBoxSeq;


	
}
