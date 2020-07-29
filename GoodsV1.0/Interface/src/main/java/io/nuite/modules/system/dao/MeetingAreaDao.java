package io.nuite.modules.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.system.entity.MeetingAreaEntity;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-04-22 16:54:27
 */
@Mapper
public interface MeetingAreaDao extends BaseMapper<MeetingAreaEntity> {
	List<MeetingAreaEntity> getMeetingAreaByName(@Param("name") String name);
}
