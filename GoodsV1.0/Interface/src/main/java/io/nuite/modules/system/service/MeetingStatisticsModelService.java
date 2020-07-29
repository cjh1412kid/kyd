package io.nuite.modules.system.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.order_platform_app.entity.MeetingStatisticsModelEntity;
import io.nuite.modules.order_platform_app.entity.OrderStatisticsModelEntity;

public interface MeetingStatisticsModelService extends IService<MeetingStatisticsModelEntity> {

	List<MeetingStatisticsModelEntity> getMeetingStatisticsModel(Integer companySeq);

    Page<MeetingStatisticsModelEntity> getMeetingStatisticsModelPage(Integer companySeq, Integer pageNum, Integer pageSize);

	boolean modelNameExisted(Integer seq, Integer companySeq, String modelName);

	Integer addMeetingStatisticsModel(MeetingStatisticsModelEntity meetingStatisticsModelEntity);

	void updateMeetingStatisticsModel(MeetingStatisticsModelEntity meetingStatisticsModelEntity);

	void deleteMeetingStatisticsModel(Integer seq);

}
