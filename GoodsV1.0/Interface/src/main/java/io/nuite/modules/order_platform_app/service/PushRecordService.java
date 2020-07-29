package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import io.nuite.modules.order_platform_app.entity.PushRecordEntity;

public interface PushRecordService {

	List<Map<String,Object>> getUnreadRecordNum(Integer userSeq);

	List<PushRecordEntity> getPushRecordList(Integer userSeq, Integer start, Integer num);

	void readPushRecord(Integer seq);

	void addPushRecord(Integer pushUserSeq, Integer receiveUserSeq, String accountName, Integer type, Integer orderSeq, String content);


}
