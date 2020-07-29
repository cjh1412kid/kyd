package io.nuite.modules.system.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MeetingStatisticsService {

	List<Object> getUserSeqByAttachTypeAndAttachSeq(Integer attachType, Integer attachSeq);

	List<Map<String, Object>> orderStatisticsList(Integer companySeq, String userSeqs,Integer meetingSeq);

}
