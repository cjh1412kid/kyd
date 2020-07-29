package io.nuite.modules.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.system.entity.MeetintUserAreaEntity;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-04-24 16:45:05
 */
@Mapper
public interface MeetintUserAreaDao extends BaseMapper<MeetintUserAreaEntity> {
	
	public MeetintUserAreaEntity getArea(@Param("areaSeq") Integer areaSeq,@Param("userSeq") Integer userSeq);
	
	public int update(MeetintUserAreaEntity MeetintUserArea);
}
