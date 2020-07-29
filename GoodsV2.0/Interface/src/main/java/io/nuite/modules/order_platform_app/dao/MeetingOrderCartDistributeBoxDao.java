package io.nuite.modules.order_platform_app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDistributeBoxEntity;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-05-31 15:09:26
 */
@Mapper
public interface MeetingOrderCartDistributeBoxDao extends BaseMapper<MeetingOrderCartDistributeBoxEntity> {
	
	List<Map<String, Object>> getTotalNumByMeetingSeq(@Param("meetingSeq")Integer meetingSeq);
}
