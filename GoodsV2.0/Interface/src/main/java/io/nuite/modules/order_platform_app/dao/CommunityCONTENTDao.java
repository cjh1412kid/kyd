package io.nuite.modules.order_platform_app.dao;

import io.nuite.modules.order_platform_app.entity.CommunityCONTENTEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:29:57
 */
@Mapper
public interface CommunityCONTENTDao extends BaseMapper<CommunityCONTENTEntity> {
	
	//工厂的所有有效用户seq
	List<Integer> getAllValidUser(@Param("companySeq")Integer companySeq,
									@Param("brandSeq")Integer brandSeq);


	//根据用户Seq，分页查询社区内容列表
	List<CommunityCONTENTEntity> getCommunityCONTENTList(@Param("allUserSeq")String allUserSeq, 
															@Param("contentTypeSeq")Integer contentTypeSeq,
															@Param("start")Integer start, 
															@Param("num")Integer num);
	
}
