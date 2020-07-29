package io.nuite.modules.online_sales_app.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.online_sales_app.entity.SalesShoppingCartDistributeBoxEntity;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-06-25 09:20:40
 */
@Mapper
public interface SalesShoppingCartDistributeBoxDao extends BaseMapper<SalesShoppingCartDistributeBoxEntity> {
	
	SalesShoppingCartDistributeBoxEntity getBySalesShoppingCartSeq(@Param("salesShoppingCartSeq")Integer salesShoppingCartSeq,@Param("colorSeq")Integer colorSeq);
}
