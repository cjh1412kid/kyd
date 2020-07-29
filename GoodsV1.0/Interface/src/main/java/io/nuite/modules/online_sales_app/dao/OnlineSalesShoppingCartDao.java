package io.nuite.modules.online_sales_app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.online_sales_app.entity.ShoppingCartEntity;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:29:57
 */
@Mapper
public interface OnlineSalesShoppingCartDao extends BaseMapper<ShoppingCartEntity> {
	
	List<Map<String, Object>> getShoppingCartListByUserSeq(Integer userSeq);
	
}
