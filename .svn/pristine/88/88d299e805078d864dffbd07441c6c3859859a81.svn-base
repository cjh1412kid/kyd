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
@TableName(DatabaseNames.SR_ONLINE_SALES +"YHSR_OLS_SalesShoppingCartDistributeBox")
public class SalesShoppingCartDistributeBoxEntity implements Serializable {
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
	 * 颜色seq
	 */
	@TableField(value = "Color_Seq")
	private Integer colorSeq;
	/**
	 * 箱数（件数）
	 */
	@TableField(value = "BoxCount")
	private Integer boxCount;
	/**
	 * 本颜色总数量
	 */
	@TableField(value = "ColorTotalNum")
	private Integer colorTotalNum;
	/**
	 * 插入时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * 配码类型 1：代码 2：具体数值
	 */
	@TableField(value = "AllocatedType")
	private Integer allocatedType;

	
}
