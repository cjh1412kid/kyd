package io.nuite.modules.order_platform_app.dao;

import io.nuite.modules.order_platform_app.entity.UserJurisdictionEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:29:58
 */
@Mapper
public interface UserJurisdictionDao extends BaseMapper<UserJurisdictionEntity> {

	List<Map<String, Object>> getCreatedUserList(Integer userSeq);
	
}
