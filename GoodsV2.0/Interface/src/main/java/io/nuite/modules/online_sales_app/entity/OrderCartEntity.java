package io.nuite.modules.online_sales_app.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;
import lombok.Data;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;

import java.io.Serializable;
import java.util.Date;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-06-25 09:20:40
 */
@Data
@TableName(DatabaseNames.SR_ONLINE_SALES +"YHSR_OLS_OrderCart")
public class OrderCartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号(主键)
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * 订货会序号
	 */
	@TableField(value = "Goods_Period_Seq")
	private Integer goodsPeriodSeq;
	/**
	 * 用户Seq(外键:YHSR_Base_User表)
	 */
	@TableField(value = "User_Seq")
	private Integer userSeq;
	/**
	 * 订货会鞋子序号
	 */
	@TableField(value = "Shoes_Seq")
	private Integer shoesSeq;
	/**
	 * 总选款数量
	 */
	@TableField(value = "TotalSelectNum")
	private Integer totalSelectNum;
	/**
	 * 每箱数量（配件）
	 */
	@TableField(value = "PerBoxNum")
	private Integer perBoxNum;
	/**
	 * 是否已配码0：否 1：是
	 */
	@TableField(value = "IsAllocated")
	private Integer isAllocated;
	/**
	 * 是否勾选，0不勾选 1勾选
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
	 * 货号
	 */
	@TableField(value = "GoodsID")
	private String goodsID;
	/**
	 * 用户贴牌货号
	 */
	@TableField(value = "UserGoodID")
	private String userGoodID;
	/**
	 * 订单Seq
	 */
	@TableField(value = "OrderSeq")
	private Integer orderSeq;

	/**
	 * 货品图片路径
	 */
	@TableField(exist = false)
	private String imgSrc;
}
