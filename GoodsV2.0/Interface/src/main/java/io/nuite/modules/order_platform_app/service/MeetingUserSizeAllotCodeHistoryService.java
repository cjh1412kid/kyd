package io.nuite.modules.order_platform_app.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.order_platform_app.entity.MeetingUserSizeAllotCodeHistoryEntity;


public interface MeetingUserSizeAllotCodeHistoryService extends IService<MeetingUserSizeAllotCodeHistoryEntity> {

	List<MeetingUserSizeAllotCodeHistoryEntity> getUserSizeAllotHistoryList(Integer userSeq, Integer minSize, Integer maxSize);

	void addUserSizeAllotCodeHistory(Integer userSeq, Integer minSize, Integer maxSize, List<Integer> sizeAllotCode);

}

