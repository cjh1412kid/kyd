package io.nuite.modules.order_platform_app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import io.nuite.modules.order_platform_app.entity.MeetingPlanEntity;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:29:58
 */
@Mapper
public interface MeetingPlanDao extends BaseMapper<MeetingPlanEntity> {

	List<Map<String, Object>> getUserMeetingPlanList(Page<Map<String, Object>> page, 
												@Param("companySeq")Integer companySeq, 
												@Param("saleType")Integer saleType, 
												@Param("periodSeq")Integer periodSeq,
												@Param("uploadState")Integer uploadState);

}
