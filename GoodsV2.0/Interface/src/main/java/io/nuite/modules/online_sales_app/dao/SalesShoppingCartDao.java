package io.nuite.modules.online_sales_app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.online_sales_app.entity.SalesShoppingCartEntity;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-06-25 09:20:40
 */
@Mapper
public interface SalesShoppingCartDao extends BaseMapper<SalesShoppingCartEntity> {
	List<Map<String, Object>> getShoppingCartListByUserSeq(Integer userSeq);
}
