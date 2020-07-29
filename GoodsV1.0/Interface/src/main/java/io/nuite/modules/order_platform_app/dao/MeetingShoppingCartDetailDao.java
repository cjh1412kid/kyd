package io.nuite.modules.order_platform_app.dao;

import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDetailEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-04-18 09:30:09
 */
@Mapper
public interface MeetingShoppingCartDetailDao extends BaseMapper<MeetingShoppingCartDetailEntity> {

	List<MeetingShoppingCartDetailEntity> selectGroupedMeetingShoppingCartDetail(@Param("meetingShoppingCartSeq") Integer meetingShoppingCartSeq);
	
}
