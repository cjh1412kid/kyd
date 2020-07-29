package io.nuite.modules.system.service.order_platform;

import java.util.List;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.order_platform_app.entity.MeetingPlanEntity;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;

public interface SysMeetingPlanService {
	PageUtils getUserPlanList(Integer companySeq, Integer saleType, Integer periodSeq, Integer uploadState, Integer pageNo, Integer pageSize);

	List<GoodsPeriodEntity> getPeriodListByBrandSeq(Integer brandSeq);

	void deleteMeetingPlan(Integer periodSeq, Integer userSeq);

	PageUtils getUserPlanDetailsList(Integer companySeq, Integer userSeq, Integer periodSeq, Integer pageNo, Integer pageSize);

	void addBatchMeetingPlan(Integer[] userSeqArr, Integer periodSeq, List<MeetingPlanEntity> meetingPlanList);
}
