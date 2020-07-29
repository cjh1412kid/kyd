package io.nuite.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuite.modules.system.entity.MeetingEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订货会管理
 * 
 * @author yangchuang
 * @email ${email}
 * @date 2019-04-16 16:13:34
 */
@Mapper
public interface MeetingDao extends BaseMapper<MeetingEntity> {
	
		List<Integer> getMeetingSeqList(@Param("userSeq") Long UserSeq);
}
