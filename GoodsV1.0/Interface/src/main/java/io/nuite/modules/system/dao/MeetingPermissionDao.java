package io.nuite.modules.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.system.entity.MeetingPermissionEntity;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-04-19 16:41:20
 */
@Mapper
public interface MeetingPermissionDao extends BaseMapper<MeetingPermissionEntity> {
	
	public List<MeetingPermissionEntity> getAllPermissionByMeetingSeq(@Param("meetingSeq") Integer meetingSeq);
	
	public MeetingPermissionEntity getPermission(@Param("meetingSeq") Integer meetingSeq,@Param("userSeq") Integer userSeq);
	
	public int update(MeetingPermissionEntity permission);
}
