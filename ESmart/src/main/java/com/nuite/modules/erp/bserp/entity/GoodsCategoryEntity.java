package com.nuite.modules.erp.bserp.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:38:09
 */

@Data
@TableName("DALEI")
public class GoodsCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号(主键)
	 */
	@TableField(value = "DLDM")
	private String seq;

	/**
	 * 分类名称
	 */
	@TableField(value = "DLMC")
	private String name;

}
