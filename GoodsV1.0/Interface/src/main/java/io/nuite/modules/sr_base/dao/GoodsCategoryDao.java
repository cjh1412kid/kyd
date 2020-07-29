package io.nuite.modules.sr_base.dao;

import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:38:09
 */
@Mapper
public interface GoodsCategoryDao extends BaseMapper<GoodsCategoryEntity> {

	List<Map<String, Object>> getShoesCategory(@Param("companySeq")Integer companySeq, @Param("parentSeq")Integer parentSeq);
	
}
